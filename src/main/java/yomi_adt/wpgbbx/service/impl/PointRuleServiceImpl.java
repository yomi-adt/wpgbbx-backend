package yomi_adt.wpgbbx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yomi_adt.wpgbbx.dto.PointRuleDtos.PointRuleRequest;
import yomi_adt.wpgbbx.model.PointRule;
import yomi_adt.wpgbbx.repository.PointRuleRepository;
import yomi_adt.wpgbbx.service.exceptions.PointRuleNotFoundException;
import yomi_adt.wpgbbx.service.PointRuleService;

import java.util.List;

@Service
public class PointRuleServiceImpl implements PointRuleService {

    @Autowired
    private PointRuleRepository pointRuleRepository;

    @Override
    public List<PointRule> getAllRules() {
        return pointRuleRepository.findAll();
    }

    @Override
    public PointRule createRule(PointRuleRequest request) {
        PointRule rule = new PointRule(request.label(), request.points());
        return pointRuleRepository.save(rule);
    }

    @Override
    public PointRule updateRule(String id, PointRuleRequest request) {
        PointRule rule = pointRuleRepository.findById(id)
                .orElseThrow(() -> new PointRuleNotFoundException(id));
        rule.setLabel(request.label());
        rule.setPoints(request.points());
        return pointRuleRepository.save(rule);
    }

    @Override
    public void deleteRule(String id) {
        if (!pointRuleRepository.existsById(id)) {
            throw new PointRuleNotFoundException(id);
        }
        pointRuleRepository.deleteById(id);
    }
}