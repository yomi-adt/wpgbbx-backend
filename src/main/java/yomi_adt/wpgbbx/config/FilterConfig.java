package yomi_adt.wpgbbx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class FilterConfig {

    @Autowired
    private OrganizerProperties organizerProperties;

    /** Routes that are organizer-only for every HTTP method, no exceptions. */
    @Bean
    public FilterRegistrationBean<OrganizerAuthFilter> organizerAuthFilterRegistration() {
        FilterRegistrationBean<OrganizerAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OrganizerAuthFilter(organizerProperties.getApiKey()));

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

    /**
     * Routes where GET must stay public (roster browsing, the match-picker
     * dropdown, the Clan Scoring page's entity list) but writes still need
     * the organizer key — creating/editing/deleting a Player or Clan.
     */
    @Bean
    public FilterRegistrationBean<OrganizerAuthFilter> organizerWriteOnlyFilterRegistration() {
        FilterRegistrationBean<OrganizerAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OrganizerAuthFilter(organizerProperties.getApiKey(), Set.of("GET")));

        registration.addUrlPatterns(
                "/api/players", "/api/players/*",
                "/api/clans", "/api/clans/*");
        registration.setOrder(1);
        return registration;
    }
}