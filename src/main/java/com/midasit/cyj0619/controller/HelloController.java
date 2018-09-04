package com.midasit.cyj0619.controller;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.XmlWebApplicationContext;

@Controller
public class HelloController {
    @RequestMapping(value = "/hello")
    public String printHelloWorld() {
        return "index";
    }
}
