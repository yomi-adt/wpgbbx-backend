package yomi_adt.wpgbbx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yomi_adt.wpgbbx.service.exceptions.ChallongeApiException;
import yomi_adt.wpgbbx.service.ChallongeService;

@RestController
@RequestMapping("/api/challonge")
public class ChallongeController {

    @Autowired
    private ChallongeService challongeService;

    @GetMapping("/tournaments/{slug}")
    public ResponseEntity<?> getTournament(@PathVariable String slug) {
        try {
            return ResponseEntity.ok(challongeService.getTournamentDetail(slug));
        } catch (ChallongeApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorBody(e.getMessage()));
        }
    }

    public record ErrorBody(String message) {
    }
}