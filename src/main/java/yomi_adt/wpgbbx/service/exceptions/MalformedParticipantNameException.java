package yomi_adt.wpgbbx.service.exceptions;

/**
 * Thrown when a Challonge participant name doesn't match the expected format
 * for extracting a rank key — e.g. a clan submission missing the "[TAG] "
 * prefix entirely. Distinct from EntityNotFoundException: this means the
 * name couldn't even be parsed, not that parsing succeeded but found no match.
 */
public class MalformedParticipantNameException extends RuntimeException {

    private final String participantName;

    public MalformedParticipantNameException(String participantName, String expectedFormat) {
        super("Participant name '" + participantName + "' doesn't match the expected format: " + expectedFormat);
        this.participantName = participantName;
    }

    public String getParticipantName() {
        return participantName;
    }
}