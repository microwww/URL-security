
package com.github.microwww.security.cli.help;

import com.github.microwww.security.cli.FindService;
import com.github.microwww.security.cli.dto.Account;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author changshu.li
 */
public class HttpTokenRightFilter extends AbstractAntPathRightFilter {

    private TokenStore tokenStore = TokenStore.HEADER;
    private String tokenName = "token";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.tokenStore = TokenStore.valueOf(Rconfig.getTokenStore().toUpperCase());
        this.tokenName = Rconfig.getTokenName();
    }

    @Override
    protected Account getLogin(HttpServletRequest request) {
        String token = tokenStore.getToken(request, tokenName);
        Object em = FindService.loadCache().getCacheJson("Employee:token:" + token);
        return (Account) em;
    }

    public enum TokenStore {
        HEADER {
            @Override
            public String getToken(HttpServletRequest request, String tokenName) {
                return request.getHeader(tokenName);
            }
        },
        PARAM {
            @Override
            public String getToken(HttpServletRequest request, String tokenName) {
                return request.getParameter(tokenName);
            }
        };

        public abstract String getToken(HttpServletRequest request, String tokenName);
    }
}
