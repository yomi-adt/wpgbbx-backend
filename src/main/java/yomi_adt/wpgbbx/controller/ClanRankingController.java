package yomi_adt.wpgbbx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yomi_adt.wpgbbx.dto.RankingDtos.RankingPointRequest;
import yomi_adt.wpgbbx.service.ClanRankingService;
import yomi_adt.wpgbbx.service.exceptions.EntityNotFoundException;
import yomi_adt.wpgbbx.service.exceptions.MalformedParticipantNameException;

@RestController
@RequestMapping("/api/rankings/clans")
public class ClanRankingController {

    @Autowired
    private ClanRankingService clanRankingService;

    @PostMapping("/points")
    public ResponseEntity<?> recordPoints(@RequestBody RankingPointRequest request) {
        try {
            return ResponseEntity.ok(clanRankingService.recordPoints(request));
        } catch (EntityNotFoundException e) {
            // 409: tag was extracted fine, just no Clan exists with that tag yet.
            // Frontend should fetch /api/clans, let the admin pick or create one.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new NotFoundBody("ENTITY_NOT_FOUND", e.getParticipantName()));
        } catch (MalformedParticipantNameException e) {
            // 422: distinct from 409 — the participant name didn't even have a
            // parseable "[TAG] " prefix, so there's no tag to look up at all.
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new NotFoundBody("MALFORMED_NAME", e.getParticipantName()));
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        return ResponseEntity.ok(clanRankingService.getLeaderboard());
    }

    @GetMapping("/{tag}/entries")
    public ResponseEntity<?> getEntries(@PathVariable String tag) {
        return ResponseEntity.ok(clanRankingService.getEntriesForEntity(tag));
    }

    @PostMapping("/reset-scores")
    public ResponseEntity<?> resetAllScores() {
        long resetCount = clanRankingService.resetAllScores();
        return ResponseEntity.ok(new ResetScoresResponse(resetCount));
    }

    public record NotFoundBody(String code, String participantName) {
    }

    public record ResetScoresResponse(long entitiesReset) {
    }
}