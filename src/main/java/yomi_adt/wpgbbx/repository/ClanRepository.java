package yomi_adt.wpgbbx.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import yomi_adt.wpgbbx.model.Clan;

@Repository
public interface ClanRepository extends MongoRepository<Clan, String> {

    Optional<Clan> findByTag(String tag);

    List<Clan> findByTagContainingIgnoreCase(String tag);

    boolean existsByTag(String tag);
}
