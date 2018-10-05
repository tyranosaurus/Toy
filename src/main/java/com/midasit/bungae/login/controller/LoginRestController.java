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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    @Autowired
    AuthenticationManager authenticationManager;

    @ModelAttribute("user")
    public User createLoginUserObject() {
        return new User();
    }

    @PostMapping(path = "/doLogin")
    public Map<String, Integer> doLogin(@ModelAttribute("user") User user,
                                        HttpServletResponse response,
                                        HttpServletRequest request,
                                        HttpSession httpSession) {
        Map<String, Integer> map = new HashMap<>();

        try {
            loginService.checkLogin(user.getId(), user.getPassword());
            loginService.bindLoginUserInfo(user, userService.getById(user.getId()));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            httpSession.setAttribute("SECURITY_CONTEXT", SecurityContextHolder.getContext());

            if ( user.getAuthority().equals("ROLE_USER") ) {
                response.addHeader("redirect", request.getContextPath() + "/board/main");
            } else {
                response.addHeader("redirect", request.getContextPath() + "/admin/main");
            }
        } catch (HasNoUserException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.addHeader("redirect", request.getContextPath() + "/login/joinForm");

            map.put("ErrorCode", ServerErrorCode.HAS_NO_USER.getValue());
        }

        return map;
    }

    @PostMapping(path = "/doJoin")
    public Map<String, Object> doJoin(@RequestBody @Valid User user,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        try {
            loginService.join(user);
            response.addHeader("redirect", request.getContextPath() + "/login/loginForm");
        } catch(EmptyValueOfUserJoinException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.EMPTY_VALUE_OF_FIELD.getValue());
            map.put("ErrorMessage", e.getMessage());
        } catch (AlreadyJoinUserException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("ErrorCode", ServerErrorCode.DUPLICATION_USER_ID.getValue());
        }

        return map;
    }
}
