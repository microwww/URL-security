package com.github.microwww.security.serve.controller;

import com.github.microwww.security.cli.dto.Token;
import com.github.microwww.security.serve.config.ApiAuthenticationService;
import com.github.microwww.security.serve.domain.*;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.service.AccountService;
import com.github.microwww.security.serve.service.AuthorityService;
import com.github.microwww.security.serve.service.RoleAccountService;
import com.github.microwww.security.serve.service.RoleAuthorityService;
import com.github.microwww.security.serve.vo.AccountValue;
import com.github.microwww.security.serve.vo.AuthorityValue;
import com.github.microwww.security.serve.vo.WebappValue;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cli")
public class ClientController extends WebappAuthorController {

    public static int OVER_TIME_SECONDS = 10 * 24 * 60 * 60; // 10 DAY
    @Autowired
    RoleAuthorityService roleAuthorityService;
    @Autowired
    RoleAccountService roleAccountService;
    @Autowired
    AccountService accountService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    ApiAuthenticationService apiAuthenticationService;

    @ApiOperation("webapp登陆认证")
    @PostMapping("/auth")
    public Token author(@RequestParam String appId, @RequestParam String security) {
        Webapp webapp = webappService.findByAppId(appId).orElseThrow(() -> new ExistException.NotExist(Webapp.class));
        if (!security.equalsIgnoreCase(webapp.getAppSecurity())) {
            throw new ExistException.NotExist(Webapp.class);
        }
        GrantedAuthority authority = ApiAuthenticationService.AuthorityType.CLIENT.getAuthority();
        ApiAuthenticationService.ApiToken token = new ApiAuthenticationService.ApiToken(webapp.getAppId(), webapp.getAppSecurity(), authority);
        token.setAuthenticated(true);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        apiAuthenticationService.save(uuid, token, OVER_TIME_SECONDS);
        return new Token().setToken(uuid).setExpSeconds(OVER_TIME_SECONDS - 60);
    }

    @ApiOperation("用户登陆认证")
    @PostMapping("/login")
    public AccountValue login(@RequestParam String account, @RequestParam String password) {
        throw new UnsupportedOperationException();
    }

    @ApiOperation("用户的权限列表")
    @GetMapping("/account/authority")
    public List<AuthorityValue.Simple> authorityByAccount(@RequestParam String account) {
        Webapp login = this.getLogin();
        Account acc = accountService.findByAccount(account).orElseThrow(() -> new ExistException.NotExist(Account.class));
        Page<RoleAccount> rol = roleAccountService.findByAccount(login, acc, 0, 100);
        List<RoleAuthority> values = new ArrayList<>();
        for (RoleAccount ra : rol.getContent()) {
            Page<RoleAuthority> rl = roleAuthorityService.findByRole(ra.getRole(), 0, 1000);
            values.addAll(rl.getContent());
        }
        return values.stream().map(RoleAuthority::getAuthority).map(AuthorityValue.Simple::new).collect(Collectors.toList());
    }

    @ApiOperation("APP的权限列表")
    @GetMapping("/app/authority")
    public List<AuthorityValue.Simple> authorityByWebapp() {
        Webapp login = this.getLogin();
        Page<Authority> rol = authorityService.findByWebapp(login, 0, 100);
        return rol.stream().map(AuthorityValue.Simple::new).collect(Collectors.toList());
    }

    @ApiOperation("APP添加权限")
    @PostMapping("/authority/save.url")
    public List<AuthorityValue.Simple> saveAuthority(@RequestParam String[] url) {
        Webapp webapp = this.getLogin();
        return authorityService.save(webapp, url).stream().map(AuthorityValue.Simple::new).collect(Collectors.toList());
    }

    @ApiOperation("APP的信息")
    @GetMapping("/app.info")
    public WebappValue.Simple appInfo() {
        Webapp login = this.getLogin();
        Optional<Webapp> app = webappService.findByAppId(login.getAppId());
        return new WebappValue.Simple(app.get());
    }
}
