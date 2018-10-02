package com.midasit.bungae.login.service;

import com.midasit.bungae.exception.AlreadyJoinUserException;
import com.midasit.bungae.exception.EmptyValueOfUserJoinException;
import com.midasit.bungae.exception.HasNoUserException;
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
        checkEmptyValue(user);

        if ( !userRepository.hasId(user.getId()) ) {
            int userNo = userRepository.create(user);
            user.setNo(userNo);
            userRepository.createAuthority(user);
        } else {
            throw new AlreadyJoinUserException("이미 가입된 유저입니다.");
        }
    }

    private void checkEmptyValue(User user) {
        if ( user.getId().length() < 1 || user.getId() == null ) {
            throw new EmptyValueOfUserJoinException("아이디를 입력해 주세요.");
        }

        if ( user.getPassword().length() < 1 || user.getPassword() == null ) {
            throw new EmptyValueOfUserJoinException("패스워드를 입력해 주세요.");
        }

        if ( user.getPassword2().length() < 1 || user.getPassword2() == null ) {
            throw new EmptyValueOfUserJoinException("확인용 패스워드를 입력해 주세요.");
        }

        if ( user.getName().length() < 1 || user.getName() == null ) {
            throw new EmptyValueOfUserJoinException("이름을 입력해 주세요.");
        }

        if ( user.getEmail().length() < 1 || user.getEmail() == null ) {
            throw new EmptyValueOfUserJoinException("이메일을 입력해 주세요.");
        }

        if ( !user.getPassword().equals(user.getPassword2()) ) {
            throw new EmptyValueOfUserJoinException("비밀번호가 일치하지 않습니다. 비밀번호를 확인해 주세요.");
        }
    }
}
