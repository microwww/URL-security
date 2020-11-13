package com.github.microwww.security.cli.help;

import com.github.microwww.security.cli.dto.Employee;
import com.github.microwww.security.cli.dto.RightURL;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class AbstractRightFilter implements Filter {
    protected FilterConfig filterConfig;
    protected final AntPath antPath = new AntPath();
    // private List<String> antPaths = new CopyOnWriteArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    @Override
    public void destroy() {
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = formatURI(request);

        if (anonymous(request, path)) { // anonymous access !
            chain.doFilter(request, response);
            return;
        }

        Employee login = getLogin(request);
        if (login == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        }

        List<RightURL> right = this.getAccountUrls(login);
        for (RightURL mt : right) {
            String matcher = mt.getWebappPath();
            boolean match = antPath.match(matcher, path); // TODO :: cache
            if (match) {
                chain.doFilter(request, response);
                return;
            }
        }

        noRight(request, response, chain);
    }

    protected void noRight(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        // other is not right, 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    protected String formatURI(HttpServletRequest request) {
        String buff = request.getRequestURI();
        String base = request.getContextPath();
        String path = buff.substring(base.length());
        return path.replaceAll("//+", "/");
    }

    protected abstract boolean anonymous(HttpServletRequest request, String path);

    protected abstract List<RightURL> getAccountUrls(Employee employee);

    protected abstract Employee getLogin(HttpServletRequest request);
}
