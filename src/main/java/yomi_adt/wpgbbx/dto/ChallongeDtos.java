package yomi_adt.wpgbbx.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

public class ChallongeDtos {

    // ---------- Raw Challonge JSON:API envelope ----------

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JsonApiResource {
        public String id;
        public String type;
        public Map<String, Object> attributes;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JsonApiSingleResponse {
        public JsonApiResource data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JsonApiListResponse {
        public List<JsonApiResource> data;
    }

    // ---------- Simplified shapes returned to the frontend ----------

    public record TournamentSummary(String id, String name, String state, String tournamentType) {
    }

    public record ParticipantSummary(String id, Integer seed, String name, Integer finalRank) {
    }

    public record TournamentDetailResponse(
            TournamentSummary tournament,
            List<ParticipantSummary> participants) {
    }
}