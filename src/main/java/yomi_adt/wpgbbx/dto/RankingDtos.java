package yomi_adt.wpgbbx.dto;

import java.util.List;

public class RankingDtos {

    public record AppliedRuleInput(String label, double points, int count) {
    }

    public record RankingPointRequest(
            String tournamentId,
            String tournamentName,
            String participantName,
            /**
             * Optional: set when the admin has manually resolved participantName to a
             * specific
             * Player after a PLAYER_NOT_FOUND response, since Challonge's display name may
             * not
             * match the app's username exactly. When absent, participantName is tried
             * as-is.
             */
            String playerUsername,
            List<AppliedRuleInput> appliedRules,
            double multiplier,
            double totalPoints) {
    }

    public record RecordPointsResponse(String playerUsername, int updatedTotalPoints) {
    }

    public record LeaderboardRow(String participantName, double totalPoints, long tournamentsPlayed) {
    }
}