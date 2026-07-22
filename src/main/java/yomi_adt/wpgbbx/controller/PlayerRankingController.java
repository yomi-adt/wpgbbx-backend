package yomi_adt.wpgbbx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.service.PlayerRankingService;
import yomi_adt.wpgbbx.service.exceptions.EntityNotFoundException;
import yomi_adt.wpgbbx.service.exceptions.MalformedParticipantNameException;

@RestController
@RequestMapping("/api/rankings/players")
public class PlayerRankingController {

    @Autowired
    private PlayerRankingService playerRankingService;

    @PostMapping("/points")
    public ResponseEntity<?> recordPoints(@RequestBody RankingPointRequest request) {
        try {
            return ResponseEntity.ok(playerRankingService.recordPoints(request));
        } catch (EntityNotFoundException e) {
            // 409: frontend should fetch /api/players, let the admin pick the
            // correct one (or create a new one), and resubmit with entityKey set.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new NotFoundBody("ENTITY_NOT_FOUND", e.getParticipantName()));
        } catch (MalformedParticipantNameException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new NotFoundBody("MALFORMED_NAME", e.getParticipantName()));
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        return ResponseEntity.ok(playerRankingService.getLeaderboard());
    }

    @GetMapping("/{username}/entries")
    public ResponseEntity<?> getEntries(@PathVariable String username) {
        return ResponseEntity.ok(playerRankingService.getEntriesForEntity(username));
    }

    @PostMapping("/reset-scores")
    public ResponseEntity<?> resetAllScores() {
        var result = playerRankingService.resetAllScores();
        return ResponseEntity.ok(new ResetScoresResponse(result.entitiesReset(), result.logEntriesRemoved()));
    }

    public record NotFoundBody(String code, String participantName) {
    }

    public record ResetScoresResponse(long entitiesReset, long logEntriesRemoved) {
    }
}