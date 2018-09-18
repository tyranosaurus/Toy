package com.midasit.bungae.login.service;

import com.midasit.bungae.exception.HasNoUserException;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:web/WEB-INF/applicationContext_test.xml")
public class LoginServiceImplTest {
    @Autowired
    LoginService loginService;

    User user1 = null;
    User user2 = null;
    User user3 = null;

    @Before
    public void setUp() throws Exception {
        this.user1 = new User(1, "아이디1", "암호1", "이름1", "이메일1", Gender.MALE);
        this.user2 = new User(2, "아이디2", "암호2", "이름2", "이메일2", Gender.FEMALE);
        this.user3 = new User(3, "아이디3", "암호3", "이름3", "이메일3", Gender.MALE);
    }

    @Test
    public void 로그인_가능한지_체크한다() {
        // arrange (given)
        String id = "아이디1";
        String password = "암호1";

        // act (when)
        // assert (then)
        assertTrue(loginService.checkLogin(id, password));
    }

    @Test(expected = HasNoUserException.class)
    public void 회원가입_하지않은_유저는_로그인_못한다() {
        // arrange (given)
        String id = "없는 아이디";
        String password = "없는 비밀번호";

        // act (when)
        // assert (then)
        loginService.checkLogin(id, password);
    }

    /*@Test
    public void 로그아웃_한다() {

    }*/
}