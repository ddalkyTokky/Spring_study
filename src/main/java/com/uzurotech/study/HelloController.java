package com.uzurotech.study;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("dynamic")
    public String hello(Model model) {
        model.addAttribute("name", "이순용");
        return "dynamic";
    }
}