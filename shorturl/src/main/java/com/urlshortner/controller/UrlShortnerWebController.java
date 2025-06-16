package com.urlshortner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlShortnerWebController {

    @GetMapping("/")
    public String index() {
        return "index";  // Returns index.html from templates/
    }

}
