package com.github.microwww.security.cli.help;

import com.github.microwww.security.cli.FindService;
import com.github.microwww.security.cli.dto.Account;
import com.github.microwww.security.cli.dto.Authority;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AbstractAntPathRightFilter extends AbstractRightFilter {

    private String[] skipURL = {};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        String skips = Rconfig.getSkipurl();
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
    protected List<Authority> getAccountUrls(Account employee) {
        return FindService.loadRurlService().listUrlRight(employee.getAccount());
    }

    @Override
    protected Account getLogin(HttpServletRequest request) {
        return null;
    }
}
