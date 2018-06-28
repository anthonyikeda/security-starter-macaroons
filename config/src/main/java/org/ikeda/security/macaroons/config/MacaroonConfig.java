package org.ikeda.security.macaroons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:/macsecurity.properties")
public class MacaroonConfig {

    @Value("${security.macaroon.secret}")
    private String secret;

    @Value("${security.macaroon.location}")
    private String location;

    @Value("${security.macaroon.id}")
    private String serviceId;

    @Value("${security.macaroon.caveats.roles.enabled}")
    private boolean roleCaveatsEnabled;

    @Value("#{'${security.macaroon.caveats.roles.role}'.split(',')}")
    private List<String> roleCaveats;

    @Value("${security.macaroon.caveats.time.enabled}")
    private boolean timeCaveatEnabled;

    @Value("${security.macaroon.caveats.time.difference}")
    private Long timeCaveatDifference;

    public MacaroonConfig() { }

    public String getSecret() {
        return secret;
    }

}