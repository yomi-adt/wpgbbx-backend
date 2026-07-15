package yomi_adt.wpgbbx.service.impl;

import yomi_adt.wpgbbx.model.Clan;
import yomi_adt.wpgbbx.repository.ClanRepository;
import yomi_adt.wpgbbx.service.ClanService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClanServiceImpl implements ClanService {

    private final ClanRepository clanRepository;

    public ClanServiceImpl(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    @Override
    public Clan createClan(Clan clan) {
        return clanRepository.save(clan);
    }

    @Override
    public List<Clan> getAllClans() {
        return clanRepository.findAll();
    }

    @Override
    public Optional<Clan> getClan(String tag) {
        return clanRepository.findByTag(tag);
    }

    @Override
    public void deleteClan(String tag) {
        clanRepository.deleteById(tag);
    }
}