package yomi_adt.wpgbbx.service.impl;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.StringUtils;
import yomi_adt.wpgbbx.dto.RankingDtos.AppliedRuleInput;
import yomi_adt.wpgbbx.dto.RankingDtos.LeaderboardRow;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingEntryView;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.dto.RankingDtos.RecordPointsResponse;
import yomi_adt.wpgbbx.model.EntityType;
import yomi_adt.wpgbbx.model.RankableEntity;
import yomi_adt.wpgbbx.model.RankingPointEntry;
import yomi_adt.wpgbbx.repository.RankingPointRepository;
import yomi_adt.wpgbbx.service.RankingService;
import yomi_adt.wpgbbx.service.exceptions.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Shared implementation for "resolve a rank key -> add points -> log the
 * award".
 * Subclasses (PlayerRankingServiceImpl, ClanRankingServiceImpl) only need to
 * say
 * which repository/collection to use and how to turn a raw Challonge
 * participant
 * name into a candidate key for their entity type.
 */
public abstract class AbstractRankingService<T extends RankableEntity> implements RankingService {

        @Autowired
        private RankingPointRepository rankingPointRepository;

        @Autowired
        private MongoTemplate mongoTemplate;

        protected abstract MongoRepository<T, String> repository();

        protected abstract Class<T> entityClass();

        protected abstract EntityType entityType();

        /**
         * Turns a raw Challonge participant name into a candidate rank key for this
         * entity type. For Player this is the identity function; for Clan it parses
         * the leading "[TAG] " prefix.
         * 
         * @throws yomi_adt.wpgbbx.service.exceptions.MalformedParticipantNameException
         *                                                                              if
         *                                                                              the
         *                                                                              name
         *                                                                              doesn't
         *                                                                              match
         *                                                                              this
         *                                                                              entity
         *                                                                              type's
         *                                                                              expected
         *                                                                              format
         */
        protected abstract String extractCandidateKey(String participantName);

        @Override
        public RecordPointsResponse recordPoints(RankingPointRequest request) {
                String candidateKey = StringUtils.hasText(request.entityKey())
                                ? request.entityKey()
                                : extractCandidateKey(request.participantName());

                T entity = repository().findById(candidateKey)
                                .orElseThrow(() -> new EntityNotFoundException(entityType(),
                                                request.participantName()));

                entity.setPoints(entity.getPoints() + (int) Math.round(request.totalPoints()));
                repository().save(entity);

                RankingPointEntry entry = new RankingPointEntry();
                entry.setTournamentId(request.tournamentId());
                entry.setTournamentName(request.tournamentName());
                entry.setParticipantName(request.participantName());
                entry.setEntityType(entityType());
                entry.setEntityKey(entity.getRankKey());
                entry.setMultiplier(request.multiplier());
                entry.setTotalPoints(request.totalPoints());
                if (request.appliedRules() != null) {
                        entry.setAppliedRules(request.appliedRules().stream()
                                        .map(r -> new RankingPointEntry.AppliedRule(r.label(), r.points(), r.count()))
                                        .collect(Collectors.toList()));
                }
                rankingPointRepository.save(entry);

                return new RecordPointsResponse(entityType(), entity.getRankKey(), entity.getPoints());
        }

        @Override
        public List<LeaderboardRow> getLeaderboard() {
                Aggregation aggregation = newAggregation(
                                match(Criteria.where("entityType").is(entityType().name())),
                                group("entityKey")
                                                .sum("totalPoints").as("totalPoints")
                                                .count().as("tournamentsPlayed"),
                                sort(Sort.Direction.DESC, "totalPoints"));

                AggregationResults<LeaderboardAggregateRow> results = mongoTemplate.aggregate(aggregation,
                                "ranking_points", LeaderboardAggregateRow.class);

                return results.getMappedResults().stream()
                                .map(r -> new LeaderboardRow(r.id, r.totalPoints, r.tournamentsPlayed))
                                .collect(Collectors.toList());
        }

        @Override
        public long resetAllScores() {
                Query matchAll = new Query();
                UpdateResult result = mongoTemplate.updateMulti(matchAll, Update.update("points", 0), entityClass());
                return result.getModifiedCount();
        }

        @Override
        public List<RankingEntryView> getEntriesForEntity(String entityKey) {
                return rankingPointRepository.findByEntityTypeAndEntityKey(entityType(), entityKey).stream()
                                .sorted(Comparator.comparing(RankingPointEntry::getCreatedAt).reversed())
                                .map(entry -> new RankingEntryView(
                                                entry.getEntityKey(),
                                                entry.getTournamentName(),
                                                entry.getAppliedRules() == null
                                                                ? List.of()
                                                                : entry.getAppliedRules().stream()
                                                                                .map(r -> new AppliedRuleInput(
                                                                                                r.getLabel(),
                                                                                                r.getPoints(),
                                                                                                r.getCount()))
                                                                                .collect(Collectors.toList()),
                                                entry.getTotalPoints()))
                                .collect(Collectors.toList());
        }

        private static class LeaderboardAggregateRow {
                public String id;
                public double totalPoints;
                public long tournamentsPlayed;
        }
}