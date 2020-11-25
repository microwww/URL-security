package com.github.microwww.security.serve.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
public class ApiAuthenticationService {

    @Resource
    ValueOperations<String, Serializable> redis;

    public enum AuthorityType {
        CLIENT, USER, ADMIN;
        private final GrantedAuthority authority;

        AuthorityType() {
            this.authority = new SimpleGrantedAuthority("ROLE_" + this.name());
        }

        public GrantedAuthority getAuthority() {
            return authority;
        }

        public static GrantedAuthority[] getAllAuthority() {
            return new GrantedAuthority[]{USER.getAuthority(), ADMIN.getAuthority()};
        }
    }

    public static final String REDIS_PREFIX = "Access-token:";

    public void save(String token, ApiToken auth, long timeoutSeconds) {
        String sha2 = DigestUtils.md5DigestAsHex(token.getBytes());
        redis.set(REDIS_PREFIX + sha2, auth, timeoutSeconds, TimeUnit.SECONDS);
    }

    public Optional<ApiToken> get(String token) {
        String sha2 = DigestUtils.md5DigestAsHex(token.getBytes());
        ApiToken api = (ApiToken) redis.get(REDIS_PREFIX + sha2);
        return Optional.ofNullable(api);
    }

    public void delete(String token) {
        String sha2 = DigestUtils.md5DigestAsHex(token.getBytes());
        redis.getOperations().delete(REDIS_PREFIX + sha2);
    }

    public static class ApiToken extends AbstractAuthenticationToken {
        private static final long serialVersionUID = 1000L;
        private final String principal;
        private final String credentials;

        public ApiToken(String uuid, String password, GrantedAuthority... auth) {
            super(Arrays.asList(auth));
            this.principal = uuid;
            this.credentials = password;
        }

        @Override
        public Object getCredentials() {
            return credentials;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }
    }
}
