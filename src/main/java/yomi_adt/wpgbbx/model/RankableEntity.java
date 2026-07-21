package yomi_adt.wpgbbx.model;

/**
 * Anything that can be looked up by a stable key and awarded points: Player (by
 * username), Clan (by tag).
 */
public interface RankableEntity {
    String getRankKey();

    int getPoints();

    void setPoints(int points);
}