package com.glitch.knowledge.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


@Component
@Order(1)
@Profile("prod")
class FontAccessFilter : Filter {

    @Value("\${application.domain.url}")
    lateinit var appUrl: String

    private val extensions = Regex("\\.(woff|woff2|eot)$")

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        if (this.extensions.containsMatchIn(httpRequest.requestURI)) {
            val origin: String? = httpRequest.getHeader("Origin")
            val fetchSite: String? = httpRequest.getHeader("Sec-Fetch-Site")

            if ((origin == null && fetchSite == null)
                || (origin != null && origin != this.appUrl)
                || (fetchSite != null && fetchSite !in listOf("same-site", "same-origin"))
            ) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN)
                return
            }
        }

        chain.doFilter(httpRequest, httpResponse)
    }
}