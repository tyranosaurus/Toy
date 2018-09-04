package com.midasit.cyj0619.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @RequestMapping(value = "/main")
    public String showMain() {
        return "main";
    }

    @RequestMapping("/detailForm")
    public String showDetailForm() {
        return "detailForm";
    }

    @RequestMapping("/writeForm")
    public String showWriteForm() {
        return "writeForm";
    }

    @RequestMapping("/write")
    public String writeBoard() {
        return "redirect:/board/main";
    }

    @RequestMapping("/modifyForm")
    public String showModifyForm() {
        return "modifyForm";
    }

    @RequestMapping("/modify")
    public String modifyBoard() {
        return "redirect:/board/main";
    }
}
