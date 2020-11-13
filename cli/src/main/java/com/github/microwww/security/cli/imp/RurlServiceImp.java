
package com.github.microwww.security.cli.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.microwww.security.cli.AjaxMessage;
import com.github.microwww.security.cli.FindService;
import com.github.microwww.security.cli.NoRightException;
import com.github.microwww.security.cli.RurlService;
import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Employee;
import com.github.microwww.security.cli.dto.RightURL;

import java.io.IOException;
import java.util.*;

/**
 * @author changshu.li
 */
public class RurlServiceImp implements RurlService {

    private static ObjectMapper mapper = new ObjectMapper();
    private final String appName;
    private final String host;

    public RurlServiceImp(String appName, String host) {
        this.appName = appName;
        this.host = host;
    }

    @Override
    public Employee login(String account, String password) throws NoRightException {
        try {
            if (this.hasLoginRight(appName, account)) {
                String login = FindService.loadHttpClient().post(host + "/login", "account", account, "password", password);
                return mapper.readValue(login, Employee.class);
            }
            throw new NoRightException(-3, String.format("您（%s）没有登录该应用（%s）的权限!", account, appName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RightURL> listUrlRight(String account) {
        try {
            String login = FindService.loadHttpClient().get(host + "/account/right.url", "account", account);
            RightURL[] r = mapper.readValue(login, RightURL[].class);
            return Arrays.asList(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RightURL> listAppURL() {
        try {
            String login = FindService.loadHttpClient().get(host + "/account/right.url", "appName", appName);
            RightURL[] r = mapper.readValue(login, RightURL[].class);
            return Arrays.asList(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RightURL> listMenu(String account) {
        List<RightURL> result = new ArrayList();
        List<RightURL> list = this.listUrlRight(account);
        for (RightURL url : list) {
            if (url.getType() == 1) {
                result.add(url);
            } else if (url.getType() == 2) {
                result.add(url);
            }
        }
        Collections.sort(result, Comparator.comparingInt(RightURL::getSort));
        return result;
    }

    @Override
    public List<RightURL> saveUrlRight(Set<String> set) {
        try {
            String[] ss = new String[set.size()];
            int i = 0;
            for (String v : set) {
                ss[i++] = "url";
                ss[i++] = v;
            }
            String login = FindService.loadHttpClient().post(host + "/save/right.url", ss);
            RightURL[] r = mapper.readValue(login, RightURL[].class);
            return Arrays.asList(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public App getApplication() {
        try {
            String login = FindService.loadHttpClient().get(host + "/app.info", appName);
            return mapper.readValue(login, App.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasLoginRight(String appName, String account) {
        try {
            String login = FindService.loadHttpClient().get(host + "/login/account", "appname", appName, "account", account);
            AjaxMessage msg = mapper.readValue(login, AjaxMessage.class);
            return msg.isSuccess();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAppName() {
        return appName;
    }
}
