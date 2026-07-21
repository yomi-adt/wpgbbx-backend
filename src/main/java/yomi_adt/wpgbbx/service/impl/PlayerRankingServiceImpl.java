package yomi_adt.wpgbbx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import yomi_adt.wpgbbx.model.EntityType;
import yomi_adt.wpgbbx.model.Player;
import yomi_adt.wpgbbx.repository.PlayerRepository;
import yomi_adt.wpgbbx.service.PlayerRankingService;

@Service
public class PlayerRankingServiceImpl extends AbstractRankingService<Player> implements PlayerRankingService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    protected MongoRepository<Player, String> repository() {
        return playerRepository;
    }

    @Override
    protected Class<Player> entityClass() {
        return Player.class;
    }

    @Override
    protected EntityType entityType() {
        return EntityType.PLAYER;
    }

    @Override
    protected String extractCandidateKey(String participantName) {
        // Player participants are just their username as entered on Challonge — no
        // parsing needed.
        return participantName;
    }
}