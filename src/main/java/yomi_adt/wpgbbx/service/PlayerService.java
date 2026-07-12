package yomi_adt.wpgbbx.service;

import java.util.List;
import java.util.Optional;

import yomi_adt.wpgbbx.model.Player;

public interface PlayerService {

    Player createPlayer(Player player);

    List<Player> getAllPlayers();

    Optional<Player> getPlayer(String username);

    void deletePlayer(String username);
}
