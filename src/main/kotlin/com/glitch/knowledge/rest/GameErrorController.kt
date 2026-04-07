package com.glitch.knowledge.rest

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.boot.webmvc.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class GameErrorController : ErrorController {

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {
        val status: Int = response.status
        val path: String = request.getAttribute("jakarta.servlet.error.request_uri")?.toString() ?: "/"
        val description: String = HttpStatus.resolve(status)?.reasonPhrase ?: "Error"

        model.addAttribute("status", status)
        model.addAttribute("path", path)
        model.addAttribute("description", description)

        return "error"
    }
}
