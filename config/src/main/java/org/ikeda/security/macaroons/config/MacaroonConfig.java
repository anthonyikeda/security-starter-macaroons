package org.ikeda.security.macaroons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/macsecurity.properties")
public class MacaroonConfig {

    @Value("${security.macaroon.secret}")
    private String secret;

    @Value("${security.macaroon.location}")
    private String location;

    @Value("${security.macaroon.id}")
    private String serviceId;


    public MacaroonConfig() { }

    public String getSecret() {
        return secret;
    }

}