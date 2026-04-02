package com.glitch.knowledge.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookiePatisserie {

    public static final int COOKIE_AGE = 60 * 60 * 24 * 30 * 6;

    public static Cookie findCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("lang"))
                .findFirst();

        if (cookie.isEmpty())
            return null;

        return cookie.get();

    }

    public static ResponseCookie create(String lang) {
        ResponseCookie cookie = ResponseCookie.from("lang", lang)
                .path("/")
                .maxAge(COOKIE_AGE)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();

        return cookie;
    }

}
