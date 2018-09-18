package com.midasit.bungae.board.controller;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.EmptyValueOfBoardCreationException;
import com.midasit.bungae.board.exception.NotEqualPasswordException;
import com.midasit.bungae.board.exception.NotEqualWriterException;
import com.midasit.bungae.board.service.BoardService;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.service.BoardDetailService;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardDetailService boardDetailService;

    /* 현재 로그인한 유저라고 가정 */
    private User writer = new User(1, "아이디1", "암호1", "이름1", "이메일1", Gender.MALE);

    @GetMapping(path = "/main")
    public String showMain() {
        return "list";
    }

    @GetMapping(path = "/list")
    @ResponseBody
    public Map<String, List<Board>> readList() {
        List<Board> boards = boardService.getAll();

        Map<String, List<Board>> map = new HashMap<>();
        map.put("boards", boards);

        return map;
    }

    @GetMapping(path = "/detailForm/{boardNo}")
    public String showDetail(@PathVariable int boardNo,
                             Model model) {
        model.addAttribute("boardNo", boardNo);

        return "detail";
    }

    @GetMapping(path = "/detail")
    @ResponseBody
    public Map<String, BoardDetail> readDetail(@RequestParam int boardNo) {
        Map<String, BoardDetail> map = new HashMap<>();
        map.put("boardDetail", boardDetailService.get(boardNo));

        return map;
    }

    @GetMapping(path = "/createForm")
    @ResponseBody
    public String showCreateForm() {
        return "create";
    }

    @RequestMapping(path = "/create")
    public Map<String, Integer> create(@RequestBody Board board,
                         HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.createNew(new Board(0, board.getTitle(), board.getPassword(), null, board.getContent(), board.getMaxParticipantCount(), this.writer));
            response.setStatus(200);
            response.addHeader("redirect", "/Toy/board/main");
        } catch (EmptyValueOfBoardCreationException e) {
            response.setStatus(500);
            map.put("ErrorCode", 630);
        }

        return map;
    }

    @GetMapping(path = "/modifyForm/{boardNo}")
    public String showModifyForm(@PathVariable int boardNo,
                                 Model model) {
        model.addAttribute("boardNo", boardNo);

        return "modify";
    }

    @PostMapping(path = "/modify")
    @ResponseBody
    public Map<String, Integer> modify(@RequestBody Board board,
                                       HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.modify(board.getNo(), board.getTitle(), null, board.getContent(), board.getMaxParticipantCount(), board.getPassword(), writer.getNo());
            response.setStatus(200);
            response.addHeader("redirect", "/Toy/board/main");
        } catch (NotEqualWriterException e) {
            response.setStatus(500);
            map.put("ErrorCode", 610);
        } catch (NotEqualPasswordException e) {
            response.setStatus(500);
            map.put("ErrorCode", 620);
        }

        return map;
    }

    @PostMapping(path = "/delete")
    @ResponseBody
    public Map<String, Integer> delete2(@RequestParam Integer boardNo,
                                        @RequestParam String password,
                                        HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();
        /** 에러 일반화 -> @ControllerAdvice */
        try {
            boardService.delete(boardNo, password, writer.getNo());
        } catch (NotEqualWriterException e) {
            response.setStatus(500);
            map.put("ErrorCode", 610);
        } catch (NotEqualPasswordException e) {
            response.setStatus(500);
            map.put("ErrorCode", 620);
        }

        return map;
    }
}
