
package com.github.microwww.security.cli.imp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.microwww.security.cli.*;
import com.github.microwww.security.cli.dto.*;
import com.github.microwww.security.cli.help.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author changshu.li
 */
public class AuthorityServiceImp implements AccountAuthorityService {

    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final String appId;
    private final String appSecurity;
    private final String host;

    public AuthorityServiceImp(String host, String appId, String appSecurity) {
        this.host = host;
        this.appId = appId;
        this.appSecurity = appSecurity;
    }

    @Override
    public Token author(String appId, String appSecurity) {
        try {
            String login = FindService.loadHttpClient().post(host + "/auth", "appId", this.appId, "appSecurity", this.appSecurity);
            Token token = mapper.readValue(login, Token.class);
            if (!StringUtils.isEmpty(token.getToken())) {
                FindService.loadCache().cache("server:token", token);
            }
            return token;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Token author() {
        Token token = FindService.loadCache().getCache("server:token", Token.class);
        if (token == null || token.isExpired()) {
            token = author(appId, appSecurity);
        }
        return token;
    }

    @Override
    public Account login(String account, String password) throws NoRightException {
        try {
            String token = author().getToken();
            if (this.hasLoginRight(account)) {
                try {
                    String login = FindService.loadHttpClient().postWithHeader(
                            host + "/login",
                            Collections.singletonMap("token", token),
                            "account", account, "password", password);
                    return mapper.readValue(login, Account.class);
                } catch (HttpCodeException ex) {
                    String mess = ex.getErrorMessage();
                    ErrorMessage error = mapper.readValue(mess, ErrorMessage.class);
                    if ("HttpRequestException".equals(error.getException())) {
                        throw new NoRightException(-2, "用户名密码错误!");
                    }
                    throw ex;
                }
            }
            throw new NoRightException(-3, String.format("您（%s）没有登录该应用（%s）的权限!", account, appId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Authority> listAuthorityByAccount(String account) {
        try {
            String token = author().getToken();
            String login = FindService.loadHttpClient().getWithHeader(
                    host + "/account/authority",
                    Collections.singletonMap("token", token),
                    "account", account);
            Authority[] r = mapper.readValue(login, Authority[].class);
            return Arrays.asList(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Authority> listAuthorityByApp() {
        try {
            String token = author().getToken();
            String login = FindService.loadHttpClient().getWithHeader(
                    host + "/app/authority",
                    Collections.singletonMap("token", token),
                    "appId", appId);
            Authority[] r = mapper.readValue(login, Authority[].class);
            return Arrays.asList(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Authority> listMenu(String account) {
        List<Authority> result = new ArrayList();
        List<Authority> list = this.listAuthorityByAccount(account);
        for (Authority url : list) {
            if (Authority.Type.MENU.name().equals(url.getType())) {
                result.add(url);
            }
        }
        Collections.sort(result, Comparator.comparingInt(Authority::getSort));
        return result;
    }

    @Override
    public List<Authority> saveAuthorityUrl(Set<String> set) {
        try {
            String[] ss = new String[set.size()];
            int i = 0;
            for (String v : set) {
                ss[i++] = "url";
                ss[i++] = v;
            }
            String token = author().getToken();
            String login = FindService.loadHttpClient().postWithHeader(
                    host + "/authority/save.url",
                    Collections.singletonMap("token", token),
                    ss);
            Authority[] r = mapper.readValue(login, Authority[].class);
            return Arrays.asList(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public App getApplication() {
        try {
            String token = author().getToken();
            String login = FindService.loadHttpClient().getWithHeader(
                    host + "/app.info",
                    Collections.singletonMap("token", token),
                    appId);
            return mapper.readValue(login, App.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasLoginRight(String account) {
        try {
            String token = author().getToken();
            String login = FindService.loadHttpClient().getWithHeader(
                    host + "/app/account/login",
                    Collections.singletonMap("token", token),
                    "account", account);
            AjaxMessage msg = mapper.readValue(login, AjaxMessage.class);
            return msg.isSuccess();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAppId() {
        return appId;
    }
}
