package yomi_adt.wpgbbx.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "point_rules")
public class PointRule {

    @Id
    private String id;

    private String label;
    private double points;

    public PointRule() {
    }

    public PointRule(String label, double points) {
        this.label = label;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}