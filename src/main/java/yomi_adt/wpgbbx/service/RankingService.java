package yomi_adt.wpgbbx.service;

import yomi_adt.wpgbbx.dto.RankingDtos.LeaderboardRow;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingEntryView;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.dto.RankingDtos.RecordPointsResponse;
import yomi_adt.wpgbbx.dto.RankingDtos.ResetResult;

import java.util.List;

public interface RankingService {

        /**
         * Logs the point award and adds it to the matched entity's running total.
         * Resolution order: request.entityKey() if present, otherwise the entity's
         * own key-extraction rule is applied to request.participantName().
         * 
         * @throws yomi_adt.wpgbbx.service.exceptions.EntityNotFoundException           if
         *                                                                              no
         *                                                                              match
         *                                                                              is
         *                                                                              found
         * @throws yomi_adt.wpgbbx.service.exceptions.MalformedParticipantNameException if
         *                                                                              the
         *                                                                              participant
         *                                                                              name
         *                                                                              doesn't
         *                                                                              match
         *                                                                              the
         *                                                                              expected
         *                                                                              format
         *                                                                              for
         *                                                                              this
         *                                                                              entity
         *                                                                              type
         */
        RecordPointsResponse recordPoints(RankingPointRequest request);

        /**
         * Sums totalPoints per entity across all recorded tournaments, ranked highest
         * first.
         */
        List<LeaderboardRow> getLeaderboard();

        /**
         * Zeroes out points for every entity of this type AND deletes every
         * audit log entry (ranking_points) for this entity type — a full reset,
         * not just the running totals.
         */
        ResetResult resetAllScores();

        /**
         * Every recorded award for one entity (by username or tag), most recent first.
         */
        List<RankingEntryView> getEntriesForEntity(String entityKey);
}