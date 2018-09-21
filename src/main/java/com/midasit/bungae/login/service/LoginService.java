package com.midasit.bungae.login.service;

import com.midasit.bungae.user.dto.User;

public interface LoginService {
    boolean checkLogin(String id, String password);
    void bindLoginUserInfo(User user, User loginUserInfo);
    void join(User user);
}
