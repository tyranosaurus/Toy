package com.midasit.bungae.admin.board.controller;

import com.midasit.bungae.admin.board.dto.Notice;
import com.midasit.bungae.board.service.BoardService;
import com.midasit.bungae.errorcode.ServerErrorCode;
import com.midasit.bungae.exception.EmptyValueOfNoticeCreationException;
import com.midasit.bungae.exception.NotEqualPasswordException;
import com.midasit.bungae.exception.NotEqualWriterException;
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
@RequestMapping(path = "/admin")
public class AdminBoardRestController {
    @Autowired
    BoardService boardService;

    @GetMapping(path = "/notices")
    public Map<String, List<Notice>> readNotices() {
        Map<String, List<Notice>> map = new HashMap<>();
        List<Notice> notices = boardService.getNotices();

        map.put("notices", notices);

        return map;
    }

    @GetMapping(path = "/detail")
    public Map<String, Notice> readDetail(@RequestParam int noticeNo) {
        Map<String, Notice> map = new HashMap<>();
        map.put("notice", boardService.getNotice(noticeNo));

        return map;
    }

    @RequestMapping(path = "/createNotice")
    public Map<String, Integer> create(@RequestBody Notice notice,
                                       @ModelAttribute User user,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.createNotice(new Notice(0, notice.getTitle(), notice.getPassword(), null, notice.getContent(), user));
            response.addHeader("redirect", request.getContextPath() + "/admin/main");
        } catch (EmptyValueOfNoticeCreationException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.EMPTY_VALUE_OF_NOTICE_CREATION.getValue());
        }

        return map;
    }

    @PostMapping(path = "/modify")
    public Map<String, Integer> modify(@RequestBody Notice notice,
                                       @ModelAttribute User user,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.modifyNotice(notice.getNo(), notice.getTitle(), null, notice.getContent(), notice.getPassword(), user.getNo());
            response.addHeader("redirect", request.getContextPath() + "/admin/main");
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
    public Map<String, Integer> delete(@RequestParam Integer noticeNo,
                                       @RequestParam String password,
                                       @ModelAttribute User user,
                                       HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.deleteNotice(noticeNo, password, user.getNo());
        } catch (NotEqualWriterException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_EQUAL_WRITER.getValue());
        } catch (NotEqualPasswordException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_EQUAL_PASSWORD.getValue());
        }

        return map;
    }

    @PostMapping(path = "/deleteBoard")
    public Map<String, Integer> deleteBoard(@RequestParam Integer boardNo,
                                            @RequestParam String authority,
                                            HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<>();

        try {
            boardService.deleteByAdmin(boardNo, authority);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.NOT_ADMIN_ACCOUNT.getValue());
        }

        return map;
    }
}
