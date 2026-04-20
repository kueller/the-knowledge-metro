package com.glitch.knowledge.util

import com.glitch.knowledge.config.addCookieToResponse
import com.glitch.knowledge.config.findCookie
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*

/**
 * @param lang     Precedence of setting client lang is (from greatest to
 *                 least):
 *
 *                 1. Client passed the ?lang=(fr|en) parameter.
 *
 *                 2. Client passed a cookie with the language set.
 *
 *                 3. Look at Accept-Language header, with preference for
 *                 "fr".
 *
 *                 4. If nothing else, set "en".
 *
 * @param locale   Spring's internal parser of the Accept-Language header.
 * @param request  HTTP request.
 * @param response HTTP response.
 * @return String which is either "fr" or "en" depending on the above criteria.
 */
fun getLanguageAndSetCookie(
    lang: String?,
    locale: Locale,
    request: HttpServletRequest,
    response: HttpServletResponse
): String {
    val languages: List<String> = listOf("fr", "en")

    val cookie: Cookie? = findCookie(request)

    if (lang != null && lang in languages) {
        if (cookie?.value != lang) {
            addCookieToResponse(response, lang)
        }

        return lang
    }

    if (cookie?.value in languages) {
        cookie?.value?.let { return it }
    }

    var headerLang: String = locale.language ?: "en"

    if (cookie?.value != headerLang) {
        addCookieToResponse(response, headerLang)
    }

    return headerLang
}


/**
 * Function sends a new and updated cookie if the "lang" header is different
 * from the one in the cookie. The user can change the page language in the
 * frontend. Or if there is no cookie then one is created.
 *
 * @param request  HTTP request.
 * @param response HTTP response.
 */
fun checkAndUpdateLanguageFromHeader(request: HttpServletRequest, response: HttpServletResponse) {
    var headerLang: String = request.getHeader("lang") ?: ""

    if (headerLang in listOf("fr", "en")) {
        var cookie: Cookie? = findCookie(request)

        if (cookie?.value != headerLang) {
            addCookieToResponse(response, headerLang)
        }
    }
}