package com.glitch.knowledge.config;

import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
@Profile("prod")
public class FontAccessFilter implements Filter {

    @Value("${application.domain.url}")
    private String appUrl;

    private final Pattern extensions = Pattern.compile("\\.(woff|woff2|eot)$");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (this.extensions.matcher(httpRequest.getRequestURI()).find()) {
            String origin = httpRequest.getHeader("Origin");
            String fetchSite = httpRequest.getHeader("Sec-Fetch-Site");

            if ((origin == null && fetchSite == null)
                    || (origin != null && !origin.equals(this.appUrl))
                    || (fetchSite != null && !fetchSite.equals("same-origin"))) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
