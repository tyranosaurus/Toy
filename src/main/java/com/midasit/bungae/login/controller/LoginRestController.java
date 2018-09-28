package com.midasit.bungae.login.controller;

import com.midasit.bungae.errorcode.ServerErrorCode;
import com.midasit.bungae.exception.AlreadyJoinUserException;
import com.midasit.bungae.exception.EmptyValueOfUserJoinException;
import com.midasit.bungae.exception.HasNoUserException;
import com.midasit.bungae.login.service.LoginService;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@SessionAttributes("user")
@RequestMapping(path = "/login")
public class LoginRestController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    @ModelAttribute("user")
    public User createLoginUserObject() {
        return new User();
    }

    @PostMapping(path = "/doLogin")
    public Map<String, Integer> doLogin(@ModelAttribute("user") User user,
                                        HttpServletResponse response,
                                        HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();

        try {
            loginService.checkLogin(user.getId(), user.getPassword());
            loginService.bindLoginUserInfo(user, userService.getById(user.getId()));

            response.addHeader("redirect", request.getContextPath() + "/board/main");
        } catch (HasNoUserException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.addHeader("redirect", request.getContextPath() + "/login/joinForm");

            map.put("ErrorCode", ServerErrorCode.HAS_NO_USER.getValue());
        }

        return map;
    }

    @PostMapping(path = "/doJoin")
    public Map<String, Object> doJoin(@RequestBody User user,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        try {
            loginService.join(user);
            response.addHeader("redirect", request.getContextPath() + "/login/loginForm");
        } catch(EmptyValueOfUserJoinException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.EmptyValueOfUserJoin.getValue());
            map.put("ErrorMessage", e.getMessage());
        } catch (AlreadyJoinUserException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.DUPLICATION_USER_ID.getValue());
        }

        return map;
    }
}
