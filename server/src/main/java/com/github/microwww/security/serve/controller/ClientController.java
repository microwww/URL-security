package com.github.microwww.security.serve.controller;

import com.github.microwww.security.cli.AjaxMessage;
import com.github.microwww.security.cli.dto.Token;
import com.github.microwww.security.serve.config.ApiAuthenticationService;
import com.github.microwww.security.serve.domain.*;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.exception.HttpRequestException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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
    public Token author(@RequestParam String appId, @RequestParam String appSecurity) {
        Webapp webapp = webappService.findByAppId(appId).orElseThrow(() -> new ExistException.NotExist(Webapp.class));
        if (!appSecurity.equalsIgnoreCase(webapp.getAppSecurity())) {
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
    public AccountValue.Simple login(@RequestParam String account, @RequestParam String password) {
        Account acc = accountService.findByAccount(account).orElseThrow(() -> new ExistException.NotExist(Account.class));
        AuthAccount auth = accountService.findAuthAccount(acc).orElseThrow(() -> new ExistException.NotExist(AuthAccount.class));
        if (encoder.matches(password, auth.getKey())) {
            return new AccountValue.Simple(acc);
        }
        throw HttpRequestException.newI18N("username.password.error");
    }

    @ApiOperation("用户是否可登录")
    @GetMapping("/app/account/login")
    public AjaxMessage accountCanLogin(@RequestParam String account) {
        return new AjaxMessage().setData(true);
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
