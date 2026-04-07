package com.glitch.knowledge.config

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie

internal const val COOKIE_AGE: Long = 60 * 60 * 24 * 30 * 6

internal fun createCookie(lang: String): ResponseCookie {
    val cookie: ResponseCookie = ResponseCookie.from("lang", lang)
        .path("/")
        .maxAge(COOKIE_AGE)
        .httpOnly(true).secure(true)
        .sameSite("Strict")
        .build()
    return cookie
}

/**
 * From an incoming request, searches for a cookie with the "lang" parameter. If a match is found it is returned.
 */
fun findCookie(request: HttpServletRequest): Cookie? {
    val cookies = request.cookies ?: return null
    val cookie: Cookie? = cookies.firstOrNull { it.name == "lang" } ?: return null
    return cookie;
}

/**
 * Creates a new cookie to send in the header response.
 */
fun addCookieToResponse(response: HttpServletResponse, lang: String) {
    response.addHeader(HttpHeaders.SET_COOKIE, createCookie(lang).toString())
}