package com.glitch.knowledge.rest;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glitch.knowledge.model.Station;
import com.glitch.knowledge.service.StationService;
import com.glitch.knowledge.util.LanguageUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private StationService stationService;

    @Value("${application.use-mins}")
    private boolean useMins;

    @GetMapping("/")
    public String knowledge(@RequestParam(required = false) String lang, HttpServletRequest request,
            HttpServletResponse response, Locale locale, Model model) {
        List<Station> stations = stationService.getTwoRandom();

        String final_lang = LanguageUtil.getLanguageAndSetCookie(lang, locale, request, response);

        model.addAttribute("origin", stations.get(0));
        model.addAttribute("destination", stations.get(1));
        model.addAttribute("lang", final_lang);
        model.addAttribute("useMins", this.useMins);

        return "index";
    }

}
