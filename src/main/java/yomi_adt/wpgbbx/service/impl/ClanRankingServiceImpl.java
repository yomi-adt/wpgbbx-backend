package yomi_adt.wpgbbx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import yomi_adt.wpgbbx.model.Clan;
import yomi_adt.wpgbbx.model.EntityType;
import yomi_adt.wpgbbx.repository.ClanRepository;
import yomi_adt.wpgbbx.service.ClanRankingService;
import yomi_adt.wpgbbx.service.exceptions.MalformedParticipantNameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClanRankingServiceImpl extends AbstractRankingService<Clan> implements ClanRankingService {

    // "[TAG] Player/Team" -> captures TAG. Tag itself has no brackets or
    // leading/trailing whitespace.
    private static final Pattern TAG_PATTERN = Pattern.compile("^\\[([^\\[\\]]+)]\\s*.*$");

    @Autowired
    private ClanRepository clanRepository;

    @Override
    protected MongoRepository<Clan, String> repository() {
        return clanRepository;
    }

    @Override
    protected Class<Clan> entityClass() {
        return Clan.class;
    }

    @Override
    protected EntityType entityType() {
        return EntityType.CLAN;
    }

    @Override
    protected String extractCandidateKey(String participantName) {
        Matcher matcher = TAG_PATTERN.matcher(participantName == null ? "" : participantName.trim());
        if (!matcher.matches()) {
            throw new MalformedParticipantNameException(participantName, "[TAG] Player/Team");
        }
        return matcher.group(1).trim();
    }
}