package yomi_adt.wpgbbx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yomi_adt.wpgbbx.dto.PointRuleDtos.PointRuleRequest;
import yomi_adt.wpgbbx.model.PointRule;
import yomi_adt.wpgbbx.service.exceptions.PointRuleNotFoundException;
import yomi_adt.wpgbbx.service.PointRuleService;

import java.util.List;

@RestController
@RequestMapping("/api/point-rules")
public class PointRuleController {

    @Autowired
    private PointRuleService pointRuleService;

    @GetMapping
    public ResponseEntity<List<PointRule>> getAllRules() {
        return ResponseEntity.ok(pointRuleService.getAllRules());
    }

    @PostMapping
    public ResponseEntity<PointRule> createRule(@RequestBody PointRuleRequest request) {
        return ResponseEntity.ok(pointRuleService.createRule(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRule(@PathVariable String id, @RequestBody PointRuleRequest request) {
        try {
            return ResponseEntity.ok(pointRuleService.updateRule(id, request));
        } catch (PointRuleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorBody(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable String id) {
        try {
            pointRuleService.deleteRule(id);
            return ResponseEntity.noContent().build();
        } catch (PointRuleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorBody(e.getMessage()));
        }
    }

    public record ErrorBody(String message) {
    }
}