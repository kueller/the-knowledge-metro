package com.glitch.knowledge.rest;

import com.glitch.knowledge.model.Station
import com.glitch.knowledge.service.StationService
import com.glitch.knowledge.util.getLanguageAndSetCookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class HomeController(
    private val stationService: StationService,
    @param:Value("\${application.use-mins}") private val useMins: Boolean = false
) {

    @GetMapping("/")
    fun knowledge(
        @RequestParam(required = false) lang: String?,
        request: HttpServletRequest,
        response: HttpServletResponse,
        locale: Locale,
        model: Model
    ): String {
        val stationData: List<Station> = this.stationService.getTwoRandom()

        val finalLang: String = getLanguageAndSetCookie(lang, locale, request, response)

        model.addAttribute("origin", stationData[0])
        model.addAttribute("destination", stationData[1])
        model.addAttribute("lang", finalLang)
        model.addAttribute("useMins", this.useMins)

        return "index"
    }

}