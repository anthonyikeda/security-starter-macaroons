package org.ikeda.security.macaroons.authentication;

import org.springframework.security.core.GrantedAuthority;

public class MacaroonGrantedAuthority implements GrantedAuthority {

    private final String authority;

    public MacaroonGrantedAuthority(String _authority) {
        this.authority = _authority;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
