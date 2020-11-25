package com.github.microwww.security.serve.config;

import com.github.microwww.security.cli.help.StringUtils;
import com.github.microwww.security.serve.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Configuration
@Order(80)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    ApiAuthenticationService apiAuthenticationService;
    @Autowired
    GlobalExceptionHandler handler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new ApiAuthenticationFilter("/api/**", apiAuthenticationService), //
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        http//
                .requestMatchers().antMatchers("/**").and()//
                .authorizeRequests()
                .antMatchers("/api/cli/auth")
                .anonymous()
                .antMatchers("/api/cli/**")
                .hasAnyRole(ApiAuthenticationService.AuthorityType.CLIENT.name())
                .antMatchers("/api/admin/**")
                .hasAnyRole(ApiAuthenticationService.AuthorityType.ADMIN.name())
                .anyRequest().permitAll()
                .and().csrf().disable()//
                .sessionManagement().disable()//禁用 session
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin()
                .loginProcessingUrl("/login.do").permitAll();

        http.exceptionHandling().authenticationEntryPoint((r, s, e) -> {//
            handler.requestException(r, s, new AuthException.NoLogin("用户没有登录", e));
        }).accessDeniedHandler((r, s, e) -> {//
            handler.requestException(r, s, new AuthException.NoRight("登录用户没有权限", e));
        });
    }

    private CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    public static class ApiAuthenticationFilter extends GenericFilterBean {
        private RequestMatcher requiresAuthenticationRequestMatcher;
        ApiAuthenticationService redis;

        protected ApiAuthenticationFilter(String defaultFilterProcessesUrl, ApiAuthenticationService redis) {
            this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(defaultFilterProcessesUrl);
            this.redis = redis;
        }

        @Override
        public void doFilter(ServletRequest r, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) r;
            SecurityContextHolder.getContext().setAuthentication(null);
            if (requiresAuthenticationRequestMatcher.matches(request)) {
                String token = request.getHeader("token");
                if (!StringUtils.isEmpty(token)) {//
                    Optional<ApiAuthenticationService.ApiToken> auth = redis.get(token);
                    if (auth.isPresent()) {
                        if (auth.get().isAuthenticated()) {
                            SecurityContextHolder.getContext().setAuthentication(auth.get());
                        }
                    }
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
