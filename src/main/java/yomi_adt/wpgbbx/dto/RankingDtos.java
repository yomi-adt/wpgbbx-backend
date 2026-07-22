package yomi_adt.wpgbbx.dto;

import yomi_adt.wpgbbx.model.EntityType;

import java.util.List;

public class RankingDtos {

    public record AppliedRuleInput(String label, double points, int count) {
    }

    public record RankingPointRequest(
            String tournamentId,
            String tournamentName,
            /** Raw Challonge participant name, e.g. "PlayerName" or "[TAG] PlayerName". */
            String participantName,
            /**
             * Optional: set once the admin has manually resolved participantName to a
             * specific
             * entity (Player.username or Clan.tag) after an EntityNotFound/malformed
             * response.
             */
            String entityKey,
            List<AppliedRuleInput> appliedRules,
            double multiplier,
            double totalPoints) {
    }

    public record RecordPointsResponse(EntityType entityType, String entityKey, int updatedTotalPoints) {
    }

    public record LeaderboardRow(String entityKey, double totalPoints, long tournamentsPlayed) {
    }

    public record RankingEntryView(
            String entityKey,
            String tournamentName,
            List<AppliedRuleInput> appliedRules,
            double totalPoints) {
    }
}