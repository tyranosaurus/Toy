package com.midasit.bungae.board.controller;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.service.BoardService;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardServiceImpl;

    @RequestMapping(value = "/main")
    public String showMain(Model model) {
        List<Board> boards = boardServiceImpl.getAll();

        model.addAttribute("boards", boards);

        return "main";
    }

    @RequestMapping(value = "/detailForm")
    public String showDetailForm(@RequestParam int boardNo,
                                 Model model) {
        BoardDetail boardDetail = boardServiceImpl.getDetail(boardNo);

        model.addAttribute("boardDetail", boardDetail);

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
