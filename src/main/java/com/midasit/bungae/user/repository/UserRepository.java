package com.midasit.bungae.user.repository;

import com.midasit.bungae.user.dto.User;

public interface UserRepository {
    User get(int userNo);
    User get(String id);
    String getAuthority(String id);
    int hasUser(String id, String password);
    boolean hasId(String id);
    int create(User user);
}
