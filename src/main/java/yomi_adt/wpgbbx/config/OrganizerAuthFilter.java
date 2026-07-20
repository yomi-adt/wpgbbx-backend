package yomi_adt.wpgbbx.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Simple shared-secret check for organizer-only endpoints (Challonge import,
 * recording points, resetting scores, managing point rules). Not a full user
 * account system — one key, shared among organizers, sent as a header.
 *
 * Deliberately NOT a @Component: it's constructed and registered explicitly
 * in FilterConfig so it only applies to specific URL patterns rather than
 * every request in the app.
 */
public class OrganizerAuthFilter implements Filter {

    public static final String HEADER_NAME = "X-Organizer-Key";

    private final String expectedApiKey;

    public OrganizerAuthFilter(String expectedApiKey) {
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Let CORS preflight through untouched — browsers don't attach custom
        // headers to the OPTIONS preflight itself, so blocking it here would
        // break every cross-origin request, not just unauthorized ones.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
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