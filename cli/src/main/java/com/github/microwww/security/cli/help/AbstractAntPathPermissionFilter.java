package com.github.microwww.security.cli.help;

import com.github.microwww.security.cli.FindService;
import com.github.microwww.security.cli.dto.Account;
import com.github.microwww.security.cli.dto.Permission;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AbstractAntPathPermissionFilter extends AbstractPermissionFilter {

    private String[] skipURL = {};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        String skips = Rconfig.getSkipUrl();
        skipURL = StringUtils.tokenizeToStringArray(skips, ",;\t", true, true);
    }

    @Override
    protected boolean anonymous(HttpServletRequest request, String path) {
        for (String skip : skipURL) {
            boolean match = antPath.match(skip, path); // TODO cache ?
            if (match) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected List<Permission> getAccountUrls(Account employee) {
        return FindService.loadAuthorityService().listPermissionByAccount(employee.getAccount());
    }

    @Override
    protected Account getLogin(HttpServletRequest request) {
        return null;
    }
}
