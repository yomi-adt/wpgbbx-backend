package yomi_adt.wpgbbx.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clans")
public class Clan {

    @Id
    private String tag;

    private String name;
    private int points;

    // Constructors
    public Clan() {
    }

    public Clan(String tag, String name, int points) {
        this.tag = tag;
        this.name = name;
        this.points = points;
    }

    // Getters and Setters

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}