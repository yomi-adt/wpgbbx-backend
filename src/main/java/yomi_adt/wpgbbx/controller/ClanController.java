package yomi_adt.wpgbbx.controller;

import yomi_adt.wpgbbx.model.Clan;
import yomi_adt.wpgbbx.service.ClanService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clans")
public class ClanController {

    private final ClanService clanService;

    public ClanController(ClanService clanService) {
        this.clanService = clanService;
    }

    @PostMapping
    public Clan createPlayer(@RequestBody Clan clan) {
        return clanService.createClan(clan);
    }

    @GetMapping
    public List<Clan> getAllClans() {
        return clanService.getAllClans();
    }
}