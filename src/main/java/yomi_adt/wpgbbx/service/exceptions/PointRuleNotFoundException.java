package yomi_adt.wpgbbx.service.exceptions;

public class PointRuleNotFoundException extends RuntimeException {
    public PointRuleNotFoundException(String id) {
        super("No point rule found with id '" + id + "'");
    }
}