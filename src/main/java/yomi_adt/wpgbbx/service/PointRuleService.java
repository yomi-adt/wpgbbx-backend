package yomi_adt.wpgbbx.service;

import yomi_adt.wpgbbx.dto.PointRuleDtos.PointRuleRequest;
import yomi_adt.wpgbbx.model.PointRule;

import java.util.List;

public interface PointRuleService {

    List<PointRule> getAllRules();

    PointRule createRule(PointRuleRequest request);

    /** @throws PointRuleNotFoundException if no rule exists with the given id */
    PointRule updateRule(String id, PointRuleRequest request);

    /** @throws PointRuleNotFoundException if no rule exists with the given id */
    void deleteRule(String id);
}