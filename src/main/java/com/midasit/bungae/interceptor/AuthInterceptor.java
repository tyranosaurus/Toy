package com.midasit.bungae.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ( request.getSession().getAttribute("user") == null ) {
            response.sendRedirect( request.getContextPath() + "/login/loginForm");

            return false;
        }

        return true;
    }
}
