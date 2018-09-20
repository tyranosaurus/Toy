package com.midasit.bungae.login.controller;

import com.midasit.bungae.error.ErrorCode;
import com.midasit.bungae.exception.HasNoUserException;
import com.midasit.bungae.login.service.LoginService;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("user")
@RequestMapping(path = "/login")
public class LoginAPIController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    @PostMapping(path = "/doLogin")
    @ResponseBody
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

            map.put("ErrorCode", ErrorCode.HAS_NO_USER.getValue());
        }

        return map;
    }

    @ModelAttribute("user")
    public User createLoginUserObject() {
        return new User();
    }

    /* 여기서는 안쓰임 */
    /*@ExceptionHandler(HttpSessionRequiredException.class)
    public String exceptionHandler(HttpSession httpSession) {
        if ( httpSession.getAttribute("user") == null ) {
            return "redirect:/login/loginForm";
        }

        return null;
    }*/
}
