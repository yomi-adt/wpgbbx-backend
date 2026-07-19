package yomi_adt.wpgbbx.service;

import yomi_adt.wpgbbx.dto.ChallongeDtos.TournamentDetailResponse;

public interface ChallongeService {

    /**
     * Fetches a tournament and its participant list from Challonge for the admin
     * dashboard.
     * 
     * @throws ChallongeApiException if the Challonge API call fails
     */
    TournamentDetailResponse getTournamentDetail(String tournamentSlugOrId);
}