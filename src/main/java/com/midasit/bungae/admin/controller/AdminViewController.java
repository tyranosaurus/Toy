package com.midasit.bungae.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminViewController {
    @GetMapping(value = "/main")
    public String showMain() {
        return "/admin/admin_main";
    }
}
