package yomi_adt.wpgbbx.service.impl;

import yomi_adt.wpgbbx.model.Player;
import yomi_adt.wpgbbx.repository.PlayerRepository;
import yomi_adt.wpgbbx.service.PlayerService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayer(String username) {
        return playerRepository.findByUsername(username);
    }

    @Override
    public void deletePlayer(String username) {
        playerRepository.deleteById(username);
    }
}