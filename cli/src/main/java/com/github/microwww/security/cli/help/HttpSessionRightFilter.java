
package com.github.microwww.security.cli.help;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.github.microwww.security.cli.dto.Employee;

/**
 *
 * @author changshu.li
 */
public class HttpSessionRightFilter extends AbstractAntPathRightFilter {

    /**
     * session 中缓存用户的KEY
     */
    private String loginSessionKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.loginSessionKey = Rconfig.getSessionKey();
    }

    @Override
    protected Employee getLogin(HttpServletRequest request) {
        return (Employee) request.getSession(true).getAttribute(loginSessionKey);
    }
}
