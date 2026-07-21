package yomi_adt.wpgbbx.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "ranking_points")
public class RankingPointEntry {

    @Id
    private String id;

    private String tournamentId;
    private String tournamentName;
    /**
     * The raw name as it came from Challonge (may differ from the resolved entity
     * key).
     */
    private String participantName;
    /** PLAYER or CLAN. */
    private EntityType entityType;
    /** The Player.username or Clan.tag this award was actually applied to. */
    private String entityKey;
    private List<AppliedRule> appliedRules;
    private double multiplier;
    private double totalPoints;
    private Instant createdAt = Instant.now();

    public static class AppliedRule {
        private String label;
        /** Per-occurrence point value from the rule at the time it was applied. */
        private double points;
        /** 1 for a BOOLEAN rule that applied; the entered count for a COUNT rule. */
        private int count = 1;

        public AppliedRule() {
        }

        public AppliedRule(String label, double points, int count) {
            this.label = label;
            this.points = points;
            this.count = count;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getPoints() {
            return points;
        }

        public void setPoints(double points) {
            this.points = points;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public List<AppliedRule> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(List<AppliedRule> appliedRules) {
        this.appliedRules = appliedRules;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}