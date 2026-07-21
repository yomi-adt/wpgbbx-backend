package yomi_adt.wpgbbx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private OrganizerProperties organizerProperties;

    @Bean
    public FilterRegistrationBean<OrganizerAuthFilter> organizerAuthFilterRegistration() {
        FilterRegistrationBean<OrganizerAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OrganizerAuthFilter(organizerProperties.getApiKey()));

        // Organizer-only routes. Public/read routes (leaderboards, player/clan
        // lists) are intentionally NOT included here — add any other admin-only
        // Player/Clan write endpoints you have (e.g. clan creation) to this list too.
        registration.addUrlPatterns(
                "/api/challonge", "/api/challonge/*",
                "/api/point-rules", "/api/point-rules/*",
                "/api/rankings/players/points",
                "/api/rankings/players/reset-scores",
                "/api/rankings/clans/points",
                "/api/rankings/clans/reset-scores");
        registration.setOrder(1);
        return registration;
    }
}