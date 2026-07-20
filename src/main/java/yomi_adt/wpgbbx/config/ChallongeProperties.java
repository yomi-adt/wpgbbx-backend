package yomi_adt.wpgbbx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "challonge")
public class ChallongeProperties {

    /**
     * Your Challonge API v1 key (from https://challonge.com/settings/developer).
     */
    private String apiKey;

    private String baseUrl = "https://api.challonge.com/v2.1/communities/wbbx";

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}