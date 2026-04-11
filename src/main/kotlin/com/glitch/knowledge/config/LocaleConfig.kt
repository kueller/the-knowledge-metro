package com.glitch.knowledge.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*


@Configuration
open class LocaleConfig {

    @Bean
    open fun gameLocaleResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.supportedLocales = listOf(Locale.FRENCH)
        resolver.setDefaultLocale(Locale.ENGLISH)
        return resolver
    }
}