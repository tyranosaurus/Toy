package com.midasit.bungae.user.repository;

import com.midasit.bungae.user.dto.User;

public interface UserRepository {
    User get(int userNo);
    int hasUser(String id, String password);
}
