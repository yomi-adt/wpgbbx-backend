package yomi_adt.wpgbbx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.service.exceptions.PlayerNotFoundException;
import yomi_adt.wpgbbx.service.RankingService;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @PostMapping("/points")
    public ResponseEntity<?> recordPoints(@RequestBody RankingPointRequest request) {
        try {
            return ResponseEntity.ok(rankingService.recordPoints(request));
        } catch (PlayerNotFoundException e) {
            // 409: the frontend should fetch /api/players, let the admin pick the
            // correct one, and resubmit with playerUsername set explicitly.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new PlayerNotFoundBody("PLAYER_NOT_FOUND", e.getParticipantName()));
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        return ResponseEntity.ok(rankingService.getLeaderboard());
    }

    public record PlayerNotFoundBody(String code, String participantName) {
    }
}