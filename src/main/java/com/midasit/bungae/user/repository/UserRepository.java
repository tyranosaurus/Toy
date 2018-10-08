package com.midasit.bungae.user.repository;

import com.midasit.bungae.generator.model.UserAuthority;
import com.midasit.bungae.user.dto.User;

public interface UserRepository {
    User get(int userNo);
    User get(String id);
    String getAuthority(String id);
    String getAuthority(int no);
    int hasUser(String id, String password);
    boolean hasId(String id);
    int create(User user);
    int createAuthority(UserAuthority userAuthority);
}
