package yomi_adt.wpgbbx.model;

public enum PointRuleType {
    /** Awarded once if true, e.g. "Made top cut?" */
    BOOLEAN,
    /**
     * Points awarded per occurrence, e.g. "X points per Swiss win" — admin enters a
     * count.
     */
    COUNT
}