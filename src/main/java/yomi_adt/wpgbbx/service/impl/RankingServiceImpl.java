package yomi_adt.wpgbbx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.mongodb.client.result.UpdateResult;
import yomi_adt.wpgbbx.dto.RankingDtos.LeaderboardRow;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.dto.RankingDtos.RecordPointsResponse;
import yomi_adt.wpgbbx.model.Player;
import yomi_adt.wpgbbx.model.RankingPointEntry;
import yomi_adt.wpgbbx.repository.PlayerRepository;
import yomi_adt.wpgbbx.repository.RankingPointRepository;
import yomi_adt.wpgbbx.service.exceptions.PlayerNotFoundException;
import yomi_adt.wpgbbx.service.RankingService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    private RankingPointRepository rankingPointRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RecordPointsResponse recordPoints(RankingPointRequest request) {
        String candidateUsername = StringUtils.hasText(request.playerUsername())
                ? request.playerUsername()
                : request.participantName();

        Player player = playerRepository.findById(candidateUsername)
                .orElseThrow(() -> new PlayerNotFoundException(request.participantName()));

        player.setPoints(player.getPoints() + (int) Math.round(request.totalPoints()));
        playerRepository.save(player);

        RankingPointEntry entry = new RankingPointEntry();
        entry.setTournamentId(request.tournamentId());
        entry.setTournamentName(request.tournamentName());
        entry.setParticipantName(request.participantName());
        entry.setPlayerUsername(player.getUsername());
        entry.setMultiplier(request.multiplier());
        entry.setTotalPoints(request.totalPoints());
        if (request.appliedRules() != null) {
            entry.setAppliedRules(request.appliedRules().stream()
                    .map(r -> new RankingPointEntry.AppliedRule(r.label(), r.points(), r.count()))
                    .collect(Collectors.toList()));
        }
        rankingPointRepository.save(entry);

        return new RecordPointsResponse(player.getUsername(), player.getPoints());
    }

    @Override
    public List<LeaderboardRow> getLeaderboard() {
        Aggregation aggregation = newAggregation(
                group("playerUsername")
                        .sum("totalPoints").as("totalPoints")
                        .count().as("tournamentsPlayed"),
                sort(Sort.Direction.DESC, "totalPoints"));

        AggregationResults<LeaderboardAggregateRow> results = mongoTemplate.aggregate(aggregation, "ranking_points",
                LeaderboardAggregateRow.class);

        return results.getMappedResults().stream()
                .map(r -> new LeaderboardRow(r.id, r.totalPoints, r.tournamentsPlayed))
                .collect(Collectors.toList());
    }

    @Override
    public long resetAllScores() {
        Query matchAll = new Query();
        UpdateResult result = mongoTemplate.updateMulti(matchAll, Update.update("points", 0), Player.class);
        return result.getModifiedCount();
    }

    private static class LeaderboardAggregateRow {
        public String id;
        public double totalPoints;
        public long tournamentsPlayed;
    }
}