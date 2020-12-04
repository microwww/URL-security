package com.github.microwww.security.serve.controller;

import com.github.microwww.security.cli.FindService;
import com.github.microwww.security.cli.NoRightException;
import com.github.microwww.security.cli.dto.Account;
import com.github.microwww.security.cli.dto.Token;
import com.github.microwww.security.serve.config.ApiAuthenticationService;
import com.github.microwww.security.serve.exception.HttpRequestException;
import com.github.microwww.security.serve.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RootController {

    private static final int OVER_TIME_SECONDS = 24 * 60 * 60;
    @Autowired
    AccountService accountService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ApiAuthenticationService apiAuthenticationService;

    @PostMapping("/login/account")
    public Token login(@RequestParam String username, @RequestParam String password) {
        try {
            Account login = FindService.loadAuthorityService().login(username, password);
            GrantedAuthority user = ApiAuthenticationService.AuthorityType.USER.getAuthority();
            GrantedAuthority admin = ApiAuthenticationService.AuthorityType.ADMIN.getAuthority();
            ApiAuthenticationService.ApiToken token = new ApiAuthenticationService.ApiToken(login.getAccount(), "password", user, admin);
            token.setAuthenticated(true);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            apiAuthenticationService.save(uuid, token, OVER_TIME_SECONDS);
            return new Token().setToken(uuid).setExpSeconds(OVER_TIME_SECONDS);
        } catch (NoRightException e) {
            throw new HttpRequestException(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public void logout() {
    }

}
