package com.glitch.knowledge.util;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpHeaders;

import com.glitch.knowledge.config.CookiePatisserie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LanguageUtil {
    /**
     * @param lang     Precedence of setting client lang is (from greatest to
     *                 least):<br>
     *                 1. Client passed the ?lang=(fr|en) parameter.<br>
     *                 2. Client passed a cookie with the language set.<br>
     *                 3. Look at Accept-Language header, with preference for
     *                 "fr".<br>
     *                 4. If nothing else, set "en".<br>
     * @param locale   Spring's internal parser of the Accept-Language header.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return String which is either "fr" or "en" depending on the above criteria.
     */
    public static String getLanguageAndSetCookie(String lang, Locale locale, HttpServletRequest request,
            HttpServletResponse response) {
        List<String> languages = List.of("fr", "en");

        Cookie cookie = CookiePatisserie.findCookie(request);

        if (lang != null && languages.contains(lang)) {
            if (cookie == null || !cookie.getValue().equals(lang)) {
                response.addHeader(HttpHeaders.SET_COOKIE, CookiePatisserie.create(lang).toString());
            }

            return lang;
        }

        if (cookie != null && languages.contains(cookie.getValue())) {
            return cookie.getValue();
        }

        // Falls back to "en".
        String headerLang = locale.getLanguage();

        if (cookie == null || !cookie.getValue().equals(headerLang)) {
            response.addHeader(HttpHeaders.SET_COOKIE, CookiePatisserie.create(headerLang).toString());
        }

        return headerLang;
    }

    /**
     * Function sends a new and updated cookie if the "lang" header is different
     * from the one in the cookie. The user can change the page language in the
     * frontend. Or if there is no cookie then one is created.
     * 
     * @param request  HTTP request.
     * @param response HTTP response.
     */
    public static void checkAndUpdateLanguageFromHeader(HttpServletRequest request, HttpServletResponse response) {
        String headerLang = request.getHeader("lang");
        if (headerLang == null)
            return;
        if (!List.of("fr", "en").contains(headerLang))
            return;

        Cookie cookie = CookiePatisserie.findCookie(request);

        if (cookie == null || !cookie.getValue().equals(headerLang)) {
            response.addHeader(HttpHeaders.SET_COOKIE, CookiePatisserie.create(headerLang).toString());
        }
    }
}
