package yomi_adt.wpgbbx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import yomi_adt.wpgbbx.model.PointRule;

public interface PointRuleRepository extends MongoRepository<PointRule, String> {
}