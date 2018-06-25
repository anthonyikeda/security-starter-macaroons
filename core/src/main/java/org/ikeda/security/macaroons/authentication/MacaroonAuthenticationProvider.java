package org.ikeda.security.macaroons.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;
import com.github.nitram509.jmacaroons.MacaroonsVerifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class MacaroonAuthenticationProvider extends AbstractMacaroonAuthenticationProvider {
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected UserDetails retrieveUser(String username, MacaroonAuthenticationToken authentication) throws AuthenticationException {
        logger.debug(String.format("Retrieving user %s in provider", username));
        Object authToken = authentication.getCredentials();

        Macaroon macaroon = MacaroonsBuilder.deserialize((String) authToken);
        logger.debug(String.format("Macaroons id: %s, Location: %s", macaroon.identifier, macaroon.location));

        MacaroonsVerifier verifier = new MacaroonsVerifier(macaroon);

        verifier.assertIsValid((String) authentication.getCredentials());

        MacaroonUser user = new MacaroonUser();
        user.setUsername(macaroon.identifier);
        user.setEmailAddress(macaroon.location);
        user.setPassword(macaroon.signature);

        // Since the macaroon is verified the user is considered "enabled"
        // The LoginServer will determine the field from the database. If the user is disabled, it will not issue
        // the macaroon.
        user.setEnabled(true);
        return user;
    }
}
