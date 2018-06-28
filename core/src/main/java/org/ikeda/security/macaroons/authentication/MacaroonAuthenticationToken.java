package org.ikeda.security.macaroons.authentication;

import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Since we are trying to retrofit a decentralized auth system to a system that assumes
 * a centralised authentication server there are some caveats (pun intended) we need to follow:
 *
 * <ul>
 *     <li>The macaroon id is what we will map to the principal</li>
 *     <li>Credentials will be populated with the secret of the macaroon</li>
 * </ul>
 *
 *
 */
public class MacaroonAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;
    private Macaroon macaroon;

    public MacaroonAuthenticationToken(Macaroon _macaroon, Collection<MacaroonGrantedAuthority> _authorities) {
        super(_authorities);
        this.macaroon = _macaroon;
        this.principal = macaroon.identifier;
        this.setAuthenticated(false);
    }

    public MacaroonAuthenticationToken(Macaroon _macaroon, Collection<MacaroonGrantedAuthority> _authorities, boolean _authenticated) {
        super(_authorities);
        this.macaroon = _macaroon;
        this.principal = macaroon.identifier;
        this.setAuthenticated(_authenticated);
    }
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
