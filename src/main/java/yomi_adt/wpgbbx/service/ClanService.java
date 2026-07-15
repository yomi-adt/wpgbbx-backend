package yomi_adt.wpgbbx.service;

import java.util.List;
import java.util.Optional;

import yomi_adt.wpgbbx.model.Clan;

public interface ClanService {

    Clan createClan(Clan clan);

    List<Clan> getAllClans();

    Optional<Clan> getClan(String tag);

    void deleteClan(String tag);
}
