package com.midasit.bungae.board.controller;

import com.midasit.bungae.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @RequestMapping(value = "/main")
    public String showMain() {
        return "main";
    }

    @RequestMapping(value = "/detailForm")
    public String showDetailForm() {
        return "detailForm";
    }

    @RequestMapping(value = "/writeForm")
    public String showWriteForm() {
        return "writeForm";
    }

    @RequestMapping(value = "/write")
    public String writeBoard() {
        return "redirect:/board/main";
    }

    @RequestMapping(value = "/modifyForm")
    public String showModifyForm() {
        return "modifyForm";
    }

    @RequestMapping(value = "/modify")
    public String modifyBoard() {
        return "redirect:/board/main";
    }

    @RequestMapping(value = "/delete")
    public String delete() {
        return "redirect:/board/main";
    }
}
