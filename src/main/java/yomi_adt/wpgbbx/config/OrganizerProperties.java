package yomi_adt.wpgbbx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "organizer")
public class OrganizerProperties {

    /** Shared secret organizers must send in the X-Organizer-Key header. */
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}