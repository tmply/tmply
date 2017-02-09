package com.github.cbuschka.tmply.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Integer.MIN_VALUE+1000)
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        addCORSHeaders((HttpServletRequest)request, (HttpServletResponse) response);

        if( "OPTIONS".equals(request.getMethod()))
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    private void addCORSHeaders(HttpServletRequest request, HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addIntHeader("Access-Control-Max-Age", 1728000);
    }
}
