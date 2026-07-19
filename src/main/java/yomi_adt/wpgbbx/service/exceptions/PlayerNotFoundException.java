package yomi_adt.wpgbbx.service.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    private final String participantName;

    public PlayerNotFoundException(String participantName) {
        super("No player found with username matching '" + participantName + "'");
        this.participantName = participantName;
    }

    public String getParticipantName() {
        return participantName;
    }
}