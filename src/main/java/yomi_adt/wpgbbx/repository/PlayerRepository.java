package yomi_adt.wpgbbx.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import yomi_adt.wpgbbx.model.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    Optional<Player> findByUsername(String username);

    List<Player> findByUsernameContainingIgnoreCase(String username);

    boolean existsByUsername(String username);
}
