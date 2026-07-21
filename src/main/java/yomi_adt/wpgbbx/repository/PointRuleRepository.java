package yomi_adt.wpgbbx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import yomi_adt.wpgbbx.model.EntityType;
import yomi_adt.wpgbbx.model.PointRule;

import java.util.List;

public interface PointRuleRepository extends MongoRepository<PointRule, String> {
    List<PointRule> findByAppliesTo(EntityType appliesTo);
}