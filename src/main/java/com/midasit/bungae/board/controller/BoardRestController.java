package com.midasit.bungae.board.controller;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.service.BoardService;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.service.BoardDetailService;
import com.midasit.bungae.errorcode.ServerErrorCode;
import com.midasit.bungae.exception.*;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SessionAttributes("user")
@RequestMapping(path = "/board")
public class BoardRestController {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardDetailService boardDetailService;

    @GetMapping(path = "/list")
    public Map<String, List<Board>> readList() {
        List<Board> boards = boardService.getAll();

        Map<String, List<Board>> map = new HashMap<>();
        map.put("boards", boards);

        return map;
    }

    @GetMapping(path = "/detail")
    public Map<String, BoardDetail> readDetail(@RequestParam int boardNo) {
        Map<String, BoardDetail> map = new HashMap<>();
        map.put("boardDetail", boardDetailService.get(boardNo));

        return map;
    }

    @RequestMapping(path = "/create")
    public Map<String, Integer> create(@RequestBody Board board,
                                       @ModelAttribute User user,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.createNew(new Board(0, board.getTitle(), board.getPassword(), null, board.getContent(), board.getMaxParticipantCount(), user));
            response.addHeader("redirect", request.getContextPath() + "/board/main");
        } catch (EmptyValueOfBoardCreationException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.EMPTY_VALUE_OF_BOARD_CREATION.getValue());
        } catch (MaxBoardOverflowException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.MAX_BOARD_OVERFLOW.getValue());
        }

        return map;
    }

    @PostMapping(path = "/modify")
    public Map<String, Integer> modify(@RequestBody Board board,
                                       @ModelAttribute User user,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.modify(board.getNo(), board.getTitle(), null, board.getContent(), board.getMaxParticipantCount(), board.getPassword(), user.getNo());
            response.addHeader("redirect", request.getContextPath() + "/board/main");
        } catch (NotEqualWriterException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_EQUAL_WRITER.getValue());
        } catch (NotEqualPasswordException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_EQUAL_PASSWORD.getValue());
        }

        return map;
    }

    @PostMapping(path = "/delete")
    public Map<String, Integer> delete(@RequestParam Integer boardNo,
                                       @RequestParam String password,
                                       @ModelAttribute User user,
                                       HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.delete(boardNo, password, user.getNo());
        } catch (NotEqualWriterException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_EQUAL_WRITER.getValue());
        } catch (NotEqualPasswordException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_EQUAL_PASSWORD.getValue());
        }

        return map;
    }

    @PostMapping(path = "/participate")
    public Map<String, Integer> participate(@RequestParam int boardNo,
                                            @RequestParam int userNo,
                                            HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.participate(boardNo, userNo);
        } catch (AlreadyParticipantException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.ALREADY_PARTICIPANT_USER.getValue());
        } catch (MaxParticipantOverflowInBoardException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.MAX_PARTICIPANT_OVERFLOW.getValue());
        }

        return map;
    }

    @PostMapping(path = "/cancelParticipate")
    public Map<String, Integer> cancelParticipate(@RequestParam int boardNo,
                                                  @RequestParam int userNo,
                                                  HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.cancelParticipation(boardNo, userNo);
        } catch (NoParticipantException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NO_PARTICIPANT.getValue());
        }

        return map;
    }
}
