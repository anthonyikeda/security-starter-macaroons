package org.ikeda.security.macaroons.web.servlet;

import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.ikeda.security.macaroons.authentication.MacaroonAuthenticationToken;

public class MacaroonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected final Log logger = LogFactory.getLog(getClass());

    private static final String MACAROON = "macaroon";

    public MacaroonAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    /**
     * Goal here is mixed up...
     *
     * we should be verifying the username/password to generate the macaroon for the client. but it seems to be wanting
     * to use the macaroon to do the authentication....
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        logger.debug("Attempting Authentication...");
        final String param = ofNullable(request.getHeader(AUTHORIZATION))
                .orElse(request.getParameter("t"));

        final String token = ofNullable(param)
                .map(value -> removeStart(value, MACAROON))
                .map(String::trim)
                .orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));

        logger.debug(String.format("Token is %s", token));
        Macaroon macaroon = MacaroonsBuilder.deserialize(token);
        MacaroonAuthenticationToken auth = new MacaroonAuthenticationToken(macaroon, null);
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

}