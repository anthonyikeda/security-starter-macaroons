package org.ikeda.security.macaroons.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

public class AbstractMacaroonAuthenticationProvider
        implements AuthenticationProvider, MessageSourceAware {

    protected final Log logger = LogFactory.getLog(getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Assert.isInstanceOf(MacaroonAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("MacaroonAuthenticationProvider.onlySupports",
                        "Only MacaroonAuthenticationToken is supported"));
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MacaroonAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
