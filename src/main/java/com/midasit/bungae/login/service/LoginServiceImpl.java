package com.midasit.bungae.login.service;

import com.midasit.bungae.exception.AlreadyJoinUserException;
import com.midasit.bungae.exception.EmptyValueOfUserJoinException;
import com.midasit.bungae.exception.HasNoUserException;
import com.midasit.bungae.generator.model.UserAuthority;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        user.setNo(loginUserInfo.getNo());
        user.setId(loginUserInfo.getId());
        user.setName(loginUserInfo.getName());
        user.setEmail(loginUserInfo.getEmail());
        user.setGender(loginUserInfo.getGender());
        user.setAuthority(userRepository.getAuthority(loginUserInfo.getId()));
        user.setPassword(loginUserInfo.getPassword());
    }

    @Override
    public void join(User user) {
        checkPassword(user.getPassword(), user.getPassword2());

        if ( !userRepository.hasId(user.getId()) ) {
            user.setNo(userRepository.create(user));
            userRepository.createAuthority(new UserAuthority(0, user.getId(), user.getPassword(), 1, user.getAuthority(), user.getNo()));
        } else {
            throw new AlreadyJoinUserException("이미 가입된 유저입니다.");
        }
    }

    private void checkPassword(String password1, String password2) {
        if ( password1.length() < 1 || password1 == null ) {
            throw new EmptyValueOfUserJoinException("패스워드를 입력해 주세요.");
        }

        if ( password2.length() < 1 || password2 == null ) {
            throw new EmptyValueOfUserJoinException("확인용 패스워드를 입력해 주세요.");
        }

        if ( !password1.equals(password2) ) {
            throw new EmptyValueOfUserJoinException("비밀번호가 일치하지 않습니다. 비밀번호를 확인해 주세요.");
        }
    }
}
