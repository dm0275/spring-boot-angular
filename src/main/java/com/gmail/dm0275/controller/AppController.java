package com.gmail.dm0275.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @RequestMapping("/")
    public String home(ModelMap modelMap) {
        modelMap.addAttribute("title", "CRUD Example");
        return "index";
    }

    @RequestMapping("/partials/{page}")
    public String partialHandler(@PathVariable("page") final String page) {
        return page;
    }
}
