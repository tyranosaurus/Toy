package com.midasit.bungae.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.NotEqualPasswordException;
import com.midasit.bungae.board.exception.NotEqualWriterException;
import com.midasit.bungae.board.service.BoardService;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.service.BoardDetailService;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private User writer = new User(1, "아이디1", "암호1", "이름1", "이메일1", Gender.MALE);

    @RequestMapping(value = "/list")
    public String readList(Model model) {
        List<Board> boards = boardService.getAll();

        model.addAttribute("boards", boards);

        return "list";
    }

    @RequestMapping(value = "/detail")
    public String readDetail(@RequestParam int boardNo,
                             Model model) {
        BoardDetail boardDetail = boardDetailService.get(boardNo);

        model.addAttribute("boardDetail", boardDetail);

        return "detail";
    }

    @RequestMapping(value = "/createForm")
    public String showCreateForm() {
        return "create";
    }

    @RequestMapping(value = "/create")
    public String create(@RequestParam String title,
                         @RequestParam String password,
                         @RequestParam String content,
                         @RequestParam int maxParticipantCount,
                         @RequestParam String writer) {

        boardService.createNew(new Board(0, title, password, null, content, maxParticipantCount, this.writer));

        return "redirect:/board/list";
    }

    @RequestMapping(value = "/modifyForm")
    public String showModifyForm(@RequestParam int boardNo,
                                 Model model) {
        Board board = boardService.get(boardNo);

        model.addAttribute("board", board);

        return "modify";
    }

    @RequestMapping(value = "/modify")
    public String modify(@RequestParam int boardNo,
                         @RequestParam String title,
                         @RequestParam String password,
                         @RequestParam String content,
                         @RequestParam int maxParticipantCount) {
        boardService.modify(boardNo, title, null, content, maxParticipantCount, password, writer.getNo());

        return "redirect:/board/list";
    }

    @RequestMapping(value = "/delete")
    public void delete(@RequestParam int boardNo,
                       @RequestParam String password,
                       HttpServletResponse response) {
        /** if문 보다는 try = catch로/ 커맨드 -> 예외발생. false는 구체적인 이유를 설명 못함.*/
        try {
            boardService.delete(boardNo, password, writer.getNo());
        } catch (NotEqualWriterException e) {
            response.setStatus(500); // 200, 500 으로 성공/실패 만 간단하게 구분. 세부 에러코드는 바디에 넣어서. 에러코드는 규약으로 정할 것.
            response.addIntHeader("ErrorCode", 610); // 5xx 초과 하는 값으로 커스텀 할 것.

            /** 에러 일반화 -> @ControllerAdvice */
        } catch (NotEqualPasswordException e) {
            response.setStatus(500);
            response.addIntHeader("ErrorCode", 620);
        }
    }

    @RequestMapping(value = "/allBoard")
    @ResponseBody // body에 json 으로 담아서 보내라. 이때의 키는 뭘까. , @RequestBody VS @ResponseBody

    /**
     * Q : 어떻게 @ResponseBody는 자동으로 JSON으로 바꿔주는 걸까?
     * A : http://ismydream.tistory.com/140
     *
     * */


    public Map<String, List<Board>> readAllBoard() {
        List<Board> boards = boardService.getAll();

        Map<String, List<Board>> map = new HashMap<>();
        map.put("boards", boards);

        return map;
    }
}
