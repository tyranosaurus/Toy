package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.*;
import com.midasit.bungae.boarduser.repository.BoardUserRepository;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:web/WEB-INF/applicationContext_test.xml")
public class BoardServiceTest {
    @Autowired
    BoardService boardServiceImpl;
    @Autowired
    BoardUserRepository boardUserDao;

    User user1 = null;
    User user2 = null;
    User user3 = null;

    @Before
    public void setUp() throws Exception {
        this.user1 = new User(1, "아이디1", "암호1", "이름1", "이메일1", Gender.MALE);
        this.user2 = new User(2, "아이디2", "암호2", "이름2", "이메일2", Gender.FEMALE);
        this.user3 = new User(3, "아이디3", "암호3", "이름3", "이메일3", Gender.MALE);
    }

    @After
    public void tearDown() throws Exception {
        boardServiceImpl.deleteAll();
    }

    @Test
    public void 모든_게시물을_가져온다() {
        // arrange (given)
        int boardNo1 = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        int boardNo2 = boardServiceImpl.createNew(new Board(0, "타이틀2", "패스워드2", "사진2", "내용2", 10, user2));
        int boardNo3 = boardServiceImpl.createNew(new Board(0, "타이틀3", "패스워드3", "사진3", "내용3", 3, user3));

        // act (when)
        List<Board> allBoards = boardServiceImpl.getAll();

        // assert (then)
        assertEquals(boardServiceImpl.getCount(), 3);
        isEqualAllValueOfBoard(allBoards.get(0), boardServiceImpl.get(boardNo1));
        isEqualAllValueOfBoard(allBoards.get(1), boardServiceImpl.get(boardNo2));
        isEqualAllValueOfBoard(allBoards.get(2), boardServiceImpl.get(boardNo3));
    }

    @Test
    public void 새_게시물을_작성한다() {
        // arrange (given)
        Board newBoard1 = new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1);
        Board newBoard2 = new Board(0, "타이틀2", "패스워드2", "사진2", "내용2", 10, user2);

        // act (when)
        // assert (then)
        Board getBoard1 = boardServiceImpl.get(boardServiceImpl.createNew(newBoard1));
        isEqualAllValueOfBoard(newBoard1, getBoard1);

        Board getBoard2 = boardServiceImpl.get(boardServiceImpl.createNew(newBoard2));
        isEqualAllValueOfBoard(newBoard2, getBoard2);
    }

    @Test(expected = MaxBoardOverflowException.class)
    public void 게시물은_최대_5개까지만_등록할_수_있다() {
        // arrange (given)
        boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        boardServiceImpl.createNew(new Board(0, "타이틀2", "패스워드2", "사진2", "내용2", 10, user2));
        boardServiceImpl.createNew(new Board(0, "타이틀3", "패스워드3", "사진3", "내용3", 3, user3));
        boardServiceImpl.createNew(new Board(0, "타이틀4", "패스워드4", "사진4", "내용4", 5, user1));
        boardServiceImpl.createNew(new Board(0, "타이틀5", "패스워드5", "사진5", "내용5", 10, user1));

        // act (when)
        // assert (then)
        boardServiceImpl.createNew(new Board(0, "타이틀6", "패스워드6", "사진6", "내용6", 3, user1));
    }

    @Test
    public void 작성자_아이디를_체크한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User rightUser = user1;
        User wrongUser = user2;

        // act (when)
        // assert (then)
        assertTrue(boardServiceImpl.isEqualWriter(boardNo, rightUser.getNo()));
        assertFalse(boardServiceImpl.isEqualWriter(boardNo, wrongUser.getNo()));
    }

    @Test
    public void 게시글의_패스워드를_체크한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        String rightPassword = "패스워드1";
        String wrongPassword = "틀린 패스워드";

        // act (when)
        // assert (then)
        assertTrue(boardServiceImpl.isEqualPassword(boardNo, rightPassword));
        assertFalse(boardServiceImpl.isEqualPassword(boardNo, wrongPassword));
    }

    @Test
    public void 게시글을_수정한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));

        // act (when)
        boardServiceImpl.modify(boardNo, "수정 타이틀", null, "내용", 5, "패스워드1", user1.getNo());

        // assert (then)
        Board modifiedBoard = boardServiceImpl.get(boardNo);
        assertEquals(modifiedBoard.getTitle(), "수정 타이틀");
        assertEquals(modifiedBoard.getImage(), null);
        assertEquals(modifiedBoard.getContent(), "내용");
        isEqualAllValueOfUser(modifiedBoard.getWriter(), user1);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void 게시글을_삭제한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        int currentBoardCount = boardServiceImpl.getCount();

        // act (when)
        boardServiceImpl.delete(boardNo, "패스워드1", user1.getNo());

        // assert (then)
        assertEquals(boardServiceImpl.getCount(), currentBoardCount - 1);
        assertNull(boardServiceImpl.get(boardNo));
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 작성자가_아니면_수정_및_삭제하지_못한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User wrongUser = user2;

        // act (when)
        // assert (then)
        assertFalse(boardServiceImpl.isEqualWriter(boardNo, wrongUser.getNo()));
        throw new NoRightOfModifyAndDeleteException("현재 유저와 게시글의 작성자가 일치하지 않습니다.");
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 게시글의_비밀번호가_일치하지_않으면_수정_및_삭제하지_못한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        String wrongPassword = "틀린 패스워드";

        // act (when)
        // assert (then)
        assertFalse(boardServiceImpl.isEqualPassword(boardNo, wrongPassword));
        throw new NoRightOfModifyAndDeleteException("게시글의 비밀번호가 일치하지 않습니다.");
    }

    @Test
    public void 번개모임에_참여한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User participant = user2;
        int beforeUserCount = boardServiceImpl.getParticipantCount(boardNo);

        // act (when)
        boardServiceImpl.participate(boardNo, participant.getNo());

        // assert (then)
        assertEquals(beforeUserCount + 1, boardServiceImpl.getParticipantCount(boardNo));

        List<User> participantList = boardServiceImpl.getParticipants(boardNo);
        isEqualAllValueOfUser(participantList.get(participantList.size() - 1), participant);
    }

    @Test(expected = MaxParticipantOverflowInBoardException.class)
    public void 번개모임의_최대_참여인원을_넘으면_참여_할수없다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User participant1 = new User(10, "참가 유저1", "참가 암호1", "참가 이름1", "참가 이메일1", Gender.MALE);
        User participant2 = new User(11, "참가 유저2", "참가 암호2", "참가 이름2", "참가 이메일2", Gender.FEMALE);
        User participant3 = new User(12, "참가 유저3", "참가 암호3", "참가 이름3", "참가 이메일3", Gender.MALE);
        User participant4 = new User(13, "참가 유저4", "참가 암호4", "참가 이름4", "참가 이메일4", Gender.FEMALE);
        User participant5 = new User(14, "참가 유저5", "참가 암호5", "참가 이름5", "참가 이메일5", Gender.MALE);

        // act (when)
        // assert (then)
        boardServiceImpl.participate(boardNo, participant1.getNo());
        boardServiceImpl.participate(boardNo, participant2.getNo());
        boardServiceImpl.participate(boardNo, participant3.getNo());
        boardServiceImpl.participate(boardNo, participant4.getNo());
        boardServiceImpl.participate(boardNo, participant5.getNo());
    }

    @Test(expected = AlreadyParticipantException.class)
    public void 같은_번개모임에_중복으로_참여_할수없다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User participant = user2;

        // act (when)
        // assert (then)
        boardServiceImpl.participate(boardNo, participant.getNo());
        boardServiceImpl.participate(boardNo, participant.getNo());
    }

    @Test
    public void 번개모임의_참여를_취소한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User participant = user2;

        // act (when)
        int beforeUserCount = boardServiceImpl.getParticipantCount(boardNo);
        boardServiceImpl.participate(boardNo, participant.getNo());
        boardServiceImpl.cancelParticipation(boardNo, participant.getNo());

        // assert (then)
        assertEquals(beforeUserCount, boardServiceImpl.getParticipantCount(boardNo));
        assertEquals(boardUserDao.hasParticipant(boardNo, participant.getNo()), 0);

    }

    @Test(expected = NoParticipantException.class)
    public void 참여하지_않고_참여취소는_안된다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", "패스워드1", "사진1", "내용1", 5, user1));
        User participant = user2;

        // act (when)
        // assert (then)
        boardServiceImpl.cancelParticipation(boardNo, participant.getNo());
    }

    private void isEqualAllValueOfBoard(Board board1, Board board2) {
        assertEquals(board1.getTitle(), board2.getTitle());
        assertEquals(board1.getWriter().getNo(), board2.getWriter().getNo());
        assertEquals(board1.getPassword(), board2.getPassword());
        assertEquals(board1.getImage(), board2.getImage());
        assertEquals(board1.getContent(), board2.getContent());
    }

    private void isEqualAllValueOfUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getPassword(), user2.getPassword());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(user1.getGender(), user2.getGender());
    }
}

