package org.softuni.demo.web.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/home")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ModelAndView home(HttpSession session, Authentication principal, ModelAndView modelAndView) {
        modelAndView.addObject("user", principal.getName());
        modelAndView.setViewName("home");

        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");

        return modelAndView;
    }
}
