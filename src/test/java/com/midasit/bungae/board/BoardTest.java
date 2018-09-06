package com.midasit.bungae.board;

import com.midasit.bungae.board.dao.User;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.*;
import com.midasit.bungae.board.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    BoardService boardService = null;

    User user1 = null;
    User user2 = null;
    User user3 = null;
    List<Board> boardList = null;

    @Before
    public void setUp() throws Exception {
        this.boardService = new BoardService();

        this.user1 = new User("아이디1", "암호1", "이름1", "이메일1", Gender.MALE);
        this.user2 = new User("아이디2", "암호2", "이름2", "이메일2", Gender.FEMAIL);
        this.user3 = new User("아이디3", "암호3", "이름3", "이메일3", Gender.FEMAIL);

        this.boardList = Arrays.asList(new Board(1, "타이틀1", user1, "패스워드1", "사진1", "내용1", 5),
                new Board(2, "타이틀2", user2, "패스워드2", "사진2", "내용2", 10),
                new Board(3, "타이틀3", user3, "패스워드3", "사진3", "내용3", 3));

        for ( int i = 0; i < boardList.size(); i++ ) {
            boardService.createNew(boardList.get(i));
        }
    }

    @Test
    public void 모든_게시물을_가져온다() {
        // arrange (given)
        // act (when)
        List<Board> allBoards = boardService.getAllBoard();

        // assert (then)
        assertEquals(boardService.getBoardCount(), boardList.size());
        isEqualAllValueOfBoard(allBoards.get(0), boardList.get(0));
        isEqualAllValueOfBoard(allBoards.get(1), boardList.get(1));
        isEqualAllValueOfBoard(allBoards.get(2), boardList.get(2));
    }

    @Test
    public void 새_게시물을_작성한다() {
        // arrange (given)
        Board newBoard1 = new Board(4, "타이틀4", user1, "패스워드4", "사진4", "내용4", 5);
        Board newBoard2 = new Board(5, "타이틀5", user2, "패스워드5", "사진5", "내용5", 10);

        // act (when)
        boardService.createNew(newBoard1);
        boardService.createNew(newBoard2);

        // assert (then)
        Board getBoard1 = boardService.getBoardById(newBoard1.getId());
        isEqualAllValueOfBoard(newBoard1, getBoard1);

        Board getBoard2 = boardService.getBoardById(newBoard2.getId());
        isEqualAllValueOfBoard(newBoard2, getBoard2);
    }

    @Test(expected = MaxBoardOverflowException.class)
    public void 게시물은_최대_5개까지만_등록할_수_있다() {
        // arrange (given)
        // act (when)
        boardService.createNew(new Board(4, "타이틀4", user1, "패스워드4", "사진4", "내용4", 5));
        boardService.createNew(new Board(5, "타이틀5", user1, "패스워드5", "사진5", "내용5", 10));
        // assert (then)
        boardService.createNew(new Board(6, "타이틀6", user1, "패스워드6", "사진6", "내용6", 3));
    }

    @Test
    public void 작성자_아이디를_체크한다() {
        // arrange (given)
        int boardId = 1;

        User rightUser = user1;
        User wrongUser = user2;

        // act (when)
        // assert (then)
        assertTrue(boardService.isEqualWriterId(boardId, rightUser));
        assertFalse(boardService.isEqualWriterId(boardId, wrongUser));
    }

    @Test
    public void 게시글의_패스워드를_체크한다() {
        // arrange (given)
        String rightPassword = "패스워드1";
        String wrongPassword = "틀린 패스워드";

        // act (when)
        // assert (then)
        assertTrue(boardService.isEqualPassword(1, rightPassword));
        assertFalse(boardService.isEqualPassword(1, wrongPassword));
    }

    @Test
    public void 게시글을_수정한다() {
        // arrange (given)
        int boardId = 1;

        // act (when)
        boardService.modifyBoard(boardId, "수정 타이틀", "사진", "내용");

        // assert (then)
        Board modifiedBoard = boardService.getBoardById(boardId);
        assertEquals(modifiedBoard.getTitle(), "수정 타이틀");
        assertEquals(modifiedBoard.getImage(), "사진");
        assertEquals(modifiedBoard.getContent(), "내용");
    }

    @Test
    public void 게시글을_삭제한다() {
        // arrange (given)
        int boardId = 1;
        int currentBoardCount = boardService.getBoardCount();

        // act (when)
        int deleteBoardId = boardService.deleteBoard(boardId);

        // assert (then)
        assertEquals(boardService.getBoardCount(), currentBoardCount - 1);
        assertEquals(deleteBoardId, boardId);
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 작성자가_아니면_수정_및_삭제하지_못한다() {
        // arrange (given)
        int boardId = 1;
        User wrongUser = user2;

        // act (when)
        // assert (then)
        assertFalse(boardService.isEqualWriterId(boardId, wrongUser));
        throw new NoRightOfModifyAndDeleteException("현재 유저와 게시글의 작성자가 일치하지 않습니다.");
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 게시글의_비밀번호가_일치하지_않으면_수정_및_삭제하지_못한다() {
        // arrange (given)
        int boardId = 1;
        String wrongPassword = "틀린 패스워드";

        // act (when)
        // assert (then)
        assertFalse(boardService.isEqualPassword(boardId, wrongPassword));
        throw new NoRightOfModifyAndDeleteException("게시글의 비밀번호가 일치하지 않습니다.");
    }

    @Test
    public void 번개모임에_참여한다() {
        // arrange (given)
        int boardId = 1;
        User joinUser = new User("참가 유저", "참가 암호", "참가 이름", "참가 이메일", Gender.FEMAIL);
        int beforeUserCount = boardService.getBoardById(boardId).getUserList().size();

        // act (when)
        boardService.joinUserAtBoard(boardId, joinUser);

        // assert (then)
        assertEquals(beforeUserCount + 1, boardService.getBoardById(boardId).getUserList().size());

        List<User> joinUserList = boardService.getAllUserInBoard(boardId);
        isEqualAllValueOfUser(joinUserList.get(joinUserList.size() - 1), joinUser);
    }


    @Test(expected = MaxUserOverflowInBoardException.class)
    public void 번개모임의_최대_참여인원을_넘으면_참여_할수없다() {
        // arrange (given)
        int boardId = 1;
        User joinUser1 = new User("참가 유저1", "참가 암호1", "참가 이름1", "참가 이메일1", Gender.MALE);
        User joinUser2 = new User("참가 유저2", "참가 암호2", "참가 이름2", "참가 이메일2", Gender.FEMAIL);
        User joinUser3 = new User("참가 유저3", "참가 암호3", "참가 이름3", "참가 이메일3", Gender.MALE);
        User joinUser4 = new User("참가 유저4", "참가 암호4", "참가 이름4", "참가 이메일4", Gender.FEMAIL);
        User joinUser5 = new User("참가 유저5", "참가 암호5", "참가 이름5", "참가 이메일5", Gender.MALE);

        // act (when)
        // assert (then)
        boardService.joinUserAtBoard(boardId, joinUser1);
        boardService.joinUserAtBoard(boardId, joinUser2);
        boardService.joinUserAtBoard(boardId, joinUser3);
        boardService.joinUserAtBoard(boardId, joinUser4);
        boardService.joinUserAtBoard(boardId, joinUser5);
    }

    @Test(expected = AlreadyJoinUserException.class)
    public void 같은_번개모임에_중복으로_참여_할수없다() {
        // arrange (given)
        int boardId = 1;
        User joinUser = new User("참가 유저", "참가 암호", "참가 이름", "참가 이메일", Gender.FEMAIL);

        // act (when)
        // assert (then)
        boardService.joinUserAtBoard(boardId, joinUser);
        boardService.joinUserAtBoard(boardId, joinUser);
    }

    @Test
    public void 번개모임의_참여를_취소한다() {
        // arrange (given)
        int boardId = 1;
        User joinUser = new User("참가 유저", "참가 암호", "참가 이름", "참가 이메일", Gender.FEMAIL);

        // act (when)
        int beforeUserCount = boardService.getBoardById(boardId).getUserList().size();
        boardService.joinUserAtBoard(boardId, joinUser);

        // assert (then)
        assertEquals(boardService.cancelJoinFromBoard(boardId, joinUser.getId()), joinUser.getId());
        assertEquals(beforeUserCount, boardService.getBoardById(boardId).getUserList().size());
    }

    @Test(expected = NoJoinUserException.class)
    public void 참여하지_않고_참여취소는_안된다() {
        // arrange (given)
        int boardId = 1;
        User joinUser = new User("참가 유저", "참가 암호", "참가 이름", "참가 이메일", Gender.FEMAIL);

        // act (when)
        // assert (then)
        assertEquals(boardService.cancelJoinFromBoard(boardId, joinUser.getId()), joinUser.getId());
    }

    private void isEqualAllValueOfBoard(Board board1, Board board2) {
        assertEquals(board1.getTitle(), board2.getTitle());
        assertEquals(board1.getWriter(), board2.getWriter());
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

