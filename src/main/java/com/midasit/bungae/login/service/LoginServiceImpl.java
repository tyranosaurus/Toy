package com.midasit.bungae.login.service;

import com.midasit.bungae.exception.HasNoUserException;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkLogin(String id, String password) {
        if ( userRepository.hasUser(id, password) == 1 ) {
            return true;
        }

        throw new HasNoUserException("가입된 유저가 아닙니다. 회원가입을 먼저 해주세요.");
    }

    @Override
    public void bindLoginUserInfo(User user, User loginUserInfo) {
        user.setId(loginUserInfo.getId());
        user.setName(loginUserInfo.getName());
        user.setEmail(loginUserInfo.getEmail());
        user.setGender(loginUserInfo.getGender());

        user.setPassword(null);
    }

    @Override
    public void logout() {

    }
}
