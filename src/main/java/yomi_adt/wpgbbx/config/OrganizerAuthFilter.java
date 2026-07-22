package yomi_adt.wpgbbx.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Simple shared-secret check for organizer-only endpoints. Not a full user
 * account system — one key, shared among organizers, sent as a header.
 *
 * Some routes (e.g. Challonge import, recording points) are organizer-only
 * for every HTTP method. Others (e.g. /api/players, /api/clans) need GET to
 * stay public — anyone can view the roster — while POST/PUT/DELETE on the
 * same path must still require the key. exemptMethods covers that: methods
 * listed there skip the check entirely, everything else needs a valid key.
 *
 * Deliberately NOT a @Component: it's constructed and registered explicitly
 * in FilterConfig so it only applies to specific URL patterns rather than
 * every request in the app.
 */
public class OrganizerAuthFilter implements Filter {

    public static final String HEADER_NAME = "X-Organizer-Key";

    private final String expectedApiKey;
    private final Set<String> exemptMethods;

    /**
     * No method exemptions — every request on the registered paths needs the key.
     */
    public OrganizerAuthFilter(String expectedApiKey) {
        this(expectedApiKey, Collections.emptySet());
    }

    /**
     * @param exemptMethods HTTP methods (e.g. "GET") that skip the key check
     *                      entirely.
     */
    public OrganizerAuthFilter(String expectedApiKey, Set<String> exemptMethods) {
        this.expectedApiKey = expectedApiKey;
        this.exemptMethods = exemptMethods;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String method = request.getMethod();

        // Let CORS preflight through untouched — browsers don't attach custom
        // headers to the OPTIONS preflight itself, so blocking it here would
        // break every cross-origin request, not just unauthorized ones.
        if ("OPTIONS".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

        boolean methodIsExempt = exemptMethods.stream().anyMatch(m -> m.equalsIgnoreCase(method));
        if (methodIsExempt) {
            chain.doFilter(request, response);
            return;
        }

        String providedKey = request.getHeader(HEADER_NAME);

        if (expectedApiKey == null || expectedApiKey.isBlank() || !expectedApiKey.equals(providedKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Missing or invalid organizer key\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}