package com.damoim.restapi.config.jpa;

import com.damoim.restapi.member.model.AuthUser;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        AuthUser user = (AuthUser) authentication.getPrincipal();
        return Optional.of(user.getEmail());
    }
}
