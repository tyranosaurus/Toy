package com.midasit.bungae.boarddetail.service;

import com.midasit.bungae.TestUtil;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.service.BoardService;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:web/WEB-INF/applicationContext_test.xml")
public class BoardDetailServiceTest {
    @Autowired
    BoardDetailService boardDetailService;
    @Autowired
    BoardService boardServiceImpl;
    @Autowired
    UserRepository userRepository;

    User user1 = null;
    User user2 = null;
    User user3 = null;
    User user4 = null;

    @Before
    public void setUp() throws Exception {
        this.user1 = new User(1, "아이디1", "암호1", "이름1", "이메일1", Gender.MALE);
        this.user2 = new User(2, "아이디2", "암호2", "이름2", "이메일2", Gender.FEMALE);
        this.user3 = new User(3, "아이디3", "암호3", "이름3", "이메일3", Gender.MALE);
        this.user4 = new User(4, "아이디4", "암호4", "이름4", "이메일4", Gender.FEMALE);
    }

    @After
    public void tearDown() throws Exception {
        boardServiceImpl.deleteAll();
    }

    @Test
    public void 게시판_상세정보_가져온다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User participant1 = user2;
        User participant2 = user3;
        User participant3 = user4;

        boardServiceImpl.participate(boardNo, participant1.getNo());
        boardServiceImpl.participate(boardNo, participant2.getNo());
        boardServiceImpl.participate(boardNo, participant3.getNo());

        // act (when)
        BoardDetail boardDetail = boardDetailService.get(boardNo);

        // assert (then)
        assertEquals(4, boardDetail.getParticipants().size());
        TestUtil.isEqualAllValueOfUser(boardDetail.getParticipants().get(0), user1);
        TestUtil.isEqualAllValueOfUser(boardDetail.getParticipants().get(1), participant1);
        TestUtil.isEqualAllValueOfUser(boardDetail.getParticipants().get(2), participant2);
        TestUtil.isEqualAllValueOfUser(boardDetail.getParticipants().get(3), participant3);
    }
}