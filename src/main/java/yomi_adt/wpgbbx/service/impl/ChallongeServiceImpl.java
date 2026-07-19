package yomi_adt.wpgbbx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import yomi_adt.wpgbbx.config.ChallongeProperties;
import yomi_adt.wpgbbx.dto.ChallongeDtos.*;
import yomi_adt.wpgbbx.service.exceptions.ChallongeApiException;
import yomi_adt.wpgbbx.service.ChallongeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChallongeServiceImpl implements ChallongeService {

    @Autowired
    private RestTemplate challongeRestTemplate;

    @Autowired
    private ChallongeProperties challongeProperties;

    /**
     * NOTE: verify field names against a live response once you test with
     * your API key — third-party mirrors of the v2.1 docs show slightly
     * inconsistent payload shapes, so double check "seed" / "final_rank"
     * come back as expected for your account.
     */
    @Override
    public TournamentDetailResponse getTournamentDetail(String tournamentSlugOrId) {
        String baseUrl = challongeProperties.getBaseUrl();

        JsonApiSingleResponse tournamentResp = get(
                baseUrl + "/tournaments/" + tournamentSlugOrId + ".json",
                JsonApiSingleResponse.class);
        TournamentSummary tournament = toTournamentSummary(tournamentResp.data);

        JsonApiListResponse participantsResp = get(
                baseUrl + "/tournaments/" + tournamentSlugOrId + "/participants.json",
                JsonApiListResponse.class);
        List<ParticipantSummary> participants = new ArrayList<>();
        for (JsonApiResource r : participantsResp.data) {
            participants.add(toParticipantSummary(r));
        }

        return new TournamentDetailResponse(tournament, participants);
    }

    private <T> T get(String url, Class<T> responseType) {
        try {
            ResponseEntity<T> response = challongeRestTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY,
                    responseType);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new ChallongeApiException("Challonge request failed for " + url + ": " + e.getResponseBodyAsString(),
                    e);
        }
    }

    private TournamentSummary toTournamentSummary(JsonApiResource r) {
        Map<String, Object> a = r.attributes;
        return new TournamentSummary(
                r.id,
                (String) a.get("name"),
                (String) a.get("state"),
                (String) a.get("tournament_type"));
    }

    private ParticipantSummary toParticipantSummary(JsonApiResource r) {
        Map<String, Object> a = r.attributes;
        Object seed = a.get("seed");
        Object finalRank = a.get("final_rank");
        return new ParticipantSummary(
                r.id,
                seed != null ? ((Number) seed).intValue() : null,
                (String) a.get("name"),
                finalRank != null ? ((Number) finalRank).intValue() : null);
    }
}