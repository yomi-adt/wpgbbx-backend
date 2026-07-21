package yomi_adt.wpgbbx.service.exceptions;

import yomi_adt.wpgbbx.model.EntityType;

public class EntityNotFoundException extends RuntimeException {

    private final EntityType entityType;
    private final String participantName;

    public EntityNotFoundException(EntityType entityType, String participantName) {
        super("No " + entityType.name().toLowerCase() + " found matching '" + participantName + "'");
        this.entityType = entityType;
        this.participantName = participantName;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getParticipantName() {
        return participantName;
    }
}