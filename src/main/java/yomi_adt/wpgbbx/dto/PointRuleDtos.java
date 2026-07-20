package yomi_adt.wpgbbx.dto;

import yomi_adt.wpgbbx.model.PointRuleType;

public class PointRuleDtos {

    public record PointRuleRequest(String label, double points, PointRuleType type) {
    }
}