package yomi_adt.wpgbbx.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
public class Player {

    @Id
    private String username;

    private String bladerName;
    private String lore;
    private String signatureCombo;
    private int points;

    // Constructors
    public Player() {
    }

    public Player(String username, String bladerName, String lore, String signatureCombo, int points) {
        this.username = username;
        this.bladerName = bladerName;
        this.lore = lore;
        this.signatureCombo = signatureCombo;
        this.points = points;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBladerName() {
        return bladerName;
    }

    public void setBladerName(String bladerName) {
        this.bladerName = bladerName;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getSignatureCombo() {
        return signatureCombo;
    }

    public void setSignatureCombo(String signatureCombo) {
        this.signatureCombo = signatureCombo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}