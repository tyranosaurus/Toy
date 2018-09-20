package com.midasit.bungae.user.repository;

import com.midasit.bungae.user.dto.User;

public interface UserRepository {
    User get(int userNo);
    User get(String id);
    int hasUser(String id, String password);
}
