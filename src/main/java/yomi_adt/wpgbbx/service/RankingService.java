package yomi_adt.wpgbbx.service;

import yomi_adt.wpgbbx.dto.RankingDtos.LeaderboardRow;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.dto.RankingDtos.RecordPointsResponse;

import java.util.List;

public interface RankingService {

    /**
     * Logs the point award and adds it to the matched Player's running total.
     * Resolution order: request.playerUsername() if present, otherwise
     * request.participantName() is tried as a username.
     * 
     * @throws PlayerNotFoundException if neither resolves to an existing Player
     */
    RecordPointsResponse recordPoints(RankingPointRequest request);

    /**
     * Sums totalPoints per participant across all recorded tournaments, ranked
     * highest first.
     */
    List<LeaderboardRow> getLeaderboard();

    /**
     * Zeroes out points for every Player. The ranking_points audit log is left
     * intact — this only resets the running totals, not the history of awards.
     * 
     * @return the number of players reset
     */
    long resetAllScores();
}