package yomi_adt.wpgbbx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import yomi_adt.wpgbbx.model.RankingPointEntry;

public interface RankingPointRepository extends MongoRepository<RankingPointEntry, String> {
}