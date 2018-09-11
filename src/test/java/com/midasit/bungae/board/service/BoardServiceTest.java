package com.midasit.bungae.board.service;

import com.midasit.bungae.board.Gender;
import com.midasit.bungae.board.dto.User;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.*;
import com.midasit.bungae.boardUser.repository.BoardUserDaoRepository;
import com.midasit.bungae.boardUser.repository.BoardUserRepositoryInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:web/WEB-INF/applicationContext.xml")
public class BoardServiceTest {
    @Autowired
    BoardServiceInterface boardServiceImpl;
    @Autowired
    BoardUserRepositoryInterface boardUserDaoRepository;

    User user1 = null;
    User user2 = null;
    User user3 = null;

    @Before
    public void setUp() throws Exception {
        this.user1 = new User(1, "아이디1", "암호1", "이름1", "이메일1", Gender.MALE);
        this.user2 = new User(2, "아이디2", "암호2", "이름2", "이메일2", Gender.FEMALE);
        this.user3 = new User(3, "아이디3", "암호3", "이름3", "이메일3", Gender.FEMALE);
    }

    @After
    public void tearDown() throws Exception {
        boardServiceImpl.deleteAll();
    }

    @Test
    public void 모든_게시물을_가져온다() {
        // arrange (given)
        int boardNo1 = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        int boardNo2 = boardServiceImpl.createNew(new Board(0, "타이틀2", 2, "패스워드2", "사진2", "내용2", 10));
        int boardNo3 = boardServiceImpl.createNew(new Board(0, "타이틀3", 3, "패스워드3", "사진3", "내용3", 3));

        // act (when)
        List<Board> allBoards = boardServiceImpl.getAllBoard();

        // assert (then)
        assertEquals(boardServiceImpl.getBoardCount(), 3);
        isEqualAllValueOfBoard(allBoards.get(0), boardServiceImpl.getBoardByNo(boardNo1));
        isEqualAllValueOfBoard(allBoards.get(1), boardServiceImpl.getBoardByNo(boardNo2));
        isEqualAllValueOfBoard(allBoards.get(2), boardServiceImpl.getBoardByNo(boardNo3));
    }

    @Test
    public void 새_게시물을_작성한다() {
        // arrange (given)
        Board newBoard1 = new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5);
        Board newBoard2 = new Board(0, "타이틀2", 2, "패스워드2", "사진2", "내용2", 10);

        // act (when)
        // assert (then)
        Board getBoard1 = boardServiceImpl.getBoardByNo(boardServiceImpl.createNew(newBoard1));
        isEqualAllValueOfBoard(newBoard1, getBoard1);

        Board getBoard2 = boardServiceImpl.getBoardByNo(boardServiceImpl.createNew(newBoard2));
        isEqualAllValueOfBoard(newBoard2, getBoard2);
    }

    @Test(expected = MaxBoardOverflowException.class)
    public void 게시물은_최대_5개까지만_등록할_수_있다() {
        // arrange (given)
        boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        boardServiceImpl.createNew(new Board(0, "타이틀2", 2, "패스워드2", "사진2", "내용2", 10));
        boardServiceImpl.createNew(new Board(0, "타이틀3", 3, "패스워드3", "사진3", "내용3", 3));
        boardServiceImpl.createNew(new Board(0, "타이틀4", 1, "패스워드4", "사진4", "내용4", 5));
        boardServiceImpl.createNew(new Board(0, "타이틀5", 1, "패스워드5", "사진5", "내용5", 10));

        // act (when)
        // assert (then)
        boardServiceImpl.createNew(new Board(0, "타이틀6", 1, "패스워드6", "사진6", "내용6", 3));
    }

    @Test
    public void 작성자_아이디를_체크한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));;
        User rightUser = user1;
        User wrongUser = user2;

        // act (when)
        // assert (then)
        assertTrue(boardServiceImpl.isEqualWriter(boardNo, rightUser));
        assertFalse(boardServiceImpl.isEqualWriter(boardNo, wrongUser));
    }

    @Test
    public void 게시글의_패스워드를_체크한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));;
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
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));

        // act (when)
        boardServiceImpl.modifyBoard(boardNo, "수정 타이틀", "사진", "내용");

        // assert (then)
        Board modifiedBoard = boardServiceImpl.getBoardByNo(boardNo);
        assertEquals(modifiedBoard.getTitle(), "수정 타이틀");
        assertEquals(modifiedBoard.getImage(), "사진");
        assertEquals(modifiedBoard.getContent(), "내용");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void 게시글을_삭제한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        int currentBoardCount = boardServiceImpl.getBoardCount();

        // act (when)
        boardServiceImpl.deleteBoard(boardNo);

        // assert (then)
        assertEquals(boardServiceImpl.getBoardCount(), currentBoardCount - 1);
        assertNull(boardServiceImpl.getBoardByNo(boardNo));
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 작성자가_아니면_수정_및_삭제하지_못한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        User wrongUser = user2;

        // act (when)
        // assert (then)
        assertFalse(boardServiceImpl.isEqualWriter(boardNo, wrongUser));
        throw new NoRightOfModifyAndDeleteException("현재 유저와 게시글의 작성자가 일치하지 않습니다.");
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 게시글의_비밀번호가_일치하지_않으면_수정_및_삭제하지_못한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        String wrongPassword = "틀린 패스워드";

        // act (when)
        // assert (then)
        assertFalse(boardServiceImpl.isEqualPassword(boardNo, wrongPassword));
        throw new NoRightOfModifyAndDeleteException("게시글의 비밀번호가 일치하지 않습니다.");
    }

    @Test
    public void 번개모임에_참여한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        User joinUser = user2;
        int beforeUserCount = boardServiceImpl.getUserCountOfBoard(boardNo);

        // act (when)
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser.getNo());

        // assert (then)
        assertEquals(beforeUserCount + 1, boardServiceImpl.getUserCountOfBoard(boardNo));

        List<Integer> joinUserList = boardServiceImpl.getUserNoListInBoard(boardNo);
        assertEquals(joinUserList.get(joinUserList.size() - 1).intValue(), joinUser.getNo());
    }

    @Test(expected = MaxUserOverflowInBoardException.class)
    public void 번개모임의_최대_참여인원을_넘으면_참여_할수없다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        User joinUser1 = new User(10, "참가 유저1", "참가 암호1", "참가 이름1", "참가 이메일1", Gender.MALE);
        User joinUser2 = new User(11, "참가 유저2", "참가 암호2", "참가 이름2", "참가 이메일2", Gender.FEMALE);
        User joinUser3 = new User(12, "참가 유저3", "참가 암호3", "참가 이름3", "참가 이메일3", Gender.MALE);
        User joinUser4 = new User(13, "참가 유저4", "참가 암호4", "참가 이름4", "참가 이메일4", Gender.FEMALE);
        User joinUser5 = new User(14, "참가 유저5", "참가 암호5", "참가 이름5", "참가 이메일5", Gender.MALE);

        // act (when)
        // assert (then)
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser1.getNo());
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser2.getNo());
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser3.getNo());
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser4.getNo());
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser5.getNo());
    }

    @Test(expected = AlreadyJoinUserException.class)
    public void 같은_번개모임에_중복으로_참여_할수없다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        User joinUser = user2;

        // act (when)
        // assert (then)
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser.getNo());
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser.getNo());
    }

    @Test
    public void 번개모임의_참여를_취소한다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        User joinUser = user2;

        // act (when)
        int beforeUserCount = boardServiceImpl.getUserCountOfBoard(boardNo);
        boardServiceImpl.joinUserAtBoard(boardNo, joinUser.getNo());
        boardServiceImpl.cancelJoinFromBoard(boardNo, joinUser.getNo());

        // assert (then)
        assertEquals(beforeUserCount, boardServiceImpl.getUserCountOfBoard(boardNo));
        assertEquals(boardUserDaoRepository.hasUserNoAtBoard(boardNo, joinUser.getNo()), 0);

    }

    @Test(expected = NoJoinUserException.class)
    public void 참여하지_않고_참여취소는_안된다() {
        // arrange (given)
        int boardNo = boardServiceImpl.createNew(new Board(0, "타이틀1", 1, "패스워드1", "사진1", "내용1", 5));
        User joinUser = user2;

        // act (when)
        // assert (then)
        boardServiceImpl.cancelJoinFromBoard(boardNo, joinUser.getNo());
    }

    private void isEqualAllValueOfBoard(Board board1, Board board2) {
        assertEquals(board1.getTitle(), board2.getTitle());
        assertEquals(board1.getUserNo(), board2.getUserNo());
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

