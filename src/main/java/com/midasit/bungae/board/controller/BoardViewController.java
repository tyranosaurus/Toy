package com.midasit.bungae.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/board")
public class BoardViewController {
    @GetMapping(path = "/main")
    public String showMain() {
        return "list";
    }

    @GetMapping(path = "/detailForm/{boardNo}")
    public String showDetail(@PathVariable int boardNo,
                             Model model) {
        model.addAttribute("boardNo", boardNo);

        return "detail";
    }

    @GetMapping(path = "/createForm")
    public String showCreateForm() {
        return "create";
    }

    @GetMapping(path = "/modifyForm/{boardNo}")
    public String showModifyForm(@PathVariable int boardNo,
                                 Model model) {
        model.addAttribute("boardNo", boardNo);

        return "modify";
    }
}
