package yomi_adt.wpgbbx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import yomi_adt.wpgbbx.model.EntityType;
import yomi_adt.wpgbbx.model.RankingPointEntry;

import java.util.List;

public interface RankingPointRepository extends MongoRepository<RankingPointEntry, String> {
    List<RankingPointEntry> findByEntityTypeAndEntityKey(EntityType entityType, String entityKey);
}