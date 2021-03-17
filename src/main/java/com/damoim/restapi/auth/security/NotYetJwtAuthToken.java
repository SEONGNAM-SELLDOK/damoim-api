package com.damoim.restapi.auth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.EqualsAndHashCode;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */
@EqualsAndHashCode
public class NotYetJwtAuthToken extends AbstractAuthenticationToken {

    private final String authToken;

    public NotYetJwtAuthToken(String authToken) {
        super(null);
        this.authToken = authToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return authToken;
    }
}
