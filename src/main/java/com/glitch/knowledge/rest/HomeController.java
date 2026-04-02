package com.glitch.knowledge.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glitch.knowledge.model.Station;
import com.glitch.knowledge.service.StationService;

@Controller
public class HomeController {

    @Autowired
    private StationService stationService;

    @Value("${application.use-mins}")
    private boolean useMins;

    @GetMapping("/")
    public String knowledge(@RequestParam(required = false) String lang, Model model) {
        List<Station> stations = stationService.getTwoRandom();

        if (lang == null || !List.of("en", "fr").contains(lang))
            lang = "en";

        model.addAttribute("origin", stations.get(0));
        model.addAttribute("destination", stations.get(1));
        model.addAttribute("lang", lang);
        model.addAttribute("useMins", this.useMins);

        return "index";
    }

}
