package com.exercise.tempo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Authorization, Accept, Content-type, produces");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "-1");
        HttpServletRequest request = (HttpServletRequest) req;

        if (!request.getMethod().equals("OPTIONS")) {
            try {
                chain.doFilter(req, res);
            } catch (Exception e) {
                log.error("Exception thrown ", e);
            }
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
