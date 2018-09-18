package com.midasit.bungae.login.service;

public interface LoginService {
    boolean checkLogin(String id, String password);
    void logout();
}
