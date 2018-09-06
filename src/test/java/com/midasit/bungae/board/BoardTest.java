package com.midasit.bungae.board;

import com.midasit.bungae.board.dao.User;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.AlreadyJoinUserException;
import com.midasit.bungae.board.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.exception.MaxUserOverflowInBoardException;
import com.midasit.bungae.board.service.BoardService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        boardService = new BoardService();

        user1 = new User("아이디1", "암호1", "이름1", "이메일1", Gender.MALE);
        user2 = new User("아이디2", "암호2", "이름2", "이메일2", Gender.FEMAIL);
        user3 = new User("아이디3", "암호3", "이름3", "이메일3", Gender.FEMAIL);

        boardList = Arrays.asList(new Board(1, "타이틀1", user1, "패스워드1", "사진1", "내용1", 5),
                                  new Board(2, "타이틀2", user2, "패스워드2", "사진2", "내용2", 10),
                                  new Board(3, "타이틀3", user3, "패스워드3", "사진3", "내용3", 3));

        for ( int i = 0; i < boardList.size(); i++ ) {
            boardService.createNew(boardList.get(i));
        }
    }

    @Test
    public void 모든_게시물을_가져온다() {
        List<Board> allBoards = boardService.getAllBoard();
        assertEquals(boardService.getBoardCount(), boardList.size());

        for ( int i = 0; i < boardService.getBoardCount(); i++ ) {
            checkAllValueOfBoard(allBoards.get(i), boardList.get(i));
        }
    }

    @Test
    public void 새_게시물을_작성한다() {
        Board newBoard1 = new Board(4, "타이틀4", user1, "패스워드4", "사진4", "내용4", 5);
        Board newBoard2 = new Board(5, "타이틀5", user2, "패스워드5", "사진5", "내용5", 10);

        boardService.createNew(newBoard1);
        Board getBoard1 = boardService.getBoardById(newBoard1.getId());

        checkAllValueOfBoard(newBoard1, getBoard1);

        boardService.createNew(newBoard2);
        Board getBoard2 = boardService.getBoardById(newBoard2.getId());

        checkAllValueOfBoard(newBoard2, getBoard2);
    }

    @Test(expected = MaxBoardOverflowException.class)
    public void 게시물은_최대_5개까지만_등록할_수_있다() {
        boardService.createNew(new Board(4, "타이틀4", user1, "패스워드4", "사진4", "내용4", 5));
        boardService.createNew(new Board(5, "타이틀5", user1, "패스워드5", "사진5", "내용5", 10));
        boardService.createNew(new Board(6, "타이틀6", user1, "패스워드6", "사진6", "내용6", 3));
    }

    @Test
    public void 작성자_아이디를_체크한다() {
        int boardId = 1;

        User rightUser = user1;
        User wrongUser = user2;

        boolean rightResult = boardService.checkWriterId(boardId, rightUser);
        assertTrue(rightResult); // true

        boolean wrongResult = boardService.checkWriterId(boardId, wrongUser);
        assertFalse(wrongResult); // false
    }

    @Test
    public void 게시글의_패스워드를_체크한다() {
        String rightPassword = "패스워드1";
        String wrongPassword = "틀린 패스워드";

        boolean rightResult = boardService.checkPassword(1, rightPassword);
        assertTrue(rightResult); // true

        boolean wrongResult = boardService.checkPassword(1, wrongPassword);
        assertFalse(wrongResult); // false
    }

    @Test
    public void 권한이_있으면_수정이_가능하다() {
        int boardId = 1;

        User rightUser = user1;

        if ( boardService.checkWriterId(boardId, rightUser) ) {
            String rightPassword = "패스워드1";

            if ( boardService.checkPassword(boardId, rightPassword) ) {
                String title = "수정 타이틀";
                String image = "수정 사진";
                String content = "수정 내용";

                boardService.modifyBoard(boardId, title, image, content);
            }
        }

        Board modifiedBoard = boardService.getBoardById(boardId);
        assertEquals(modifiedBoard.getTitle(), "수정 타이틀");
        assertEquals(modifiedBoard.getImage(), "수정 사진");
        assertEquals(modifiedBoard.getContent(), "수정 내용");
    }

    @Test
    public void 권한이_있으면_삭제가_가능하다() {
        int boardId = 1;
        int currentBoardCount = boardService.getBoardCount();
        User rightUser = user1;
        int deleteBoardId = -1;

        if ( boardService.checkWriterId(boardId, rightUser) ) {
            String rightPassword = "패스워드1";

            if ( boardService.checkPassword(boardId, rightPassword) ) {
                deleteBoardId = boardService.deleteBoard(boardId);
            }
        }

        assertEquals(boardService.getBoardCount(), currentBoardCount - 1);
        assertEquals(deleteBoardId, boardId);
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 게시글의_작성자가_일치하지_않으면_수정_및_삭제_권한이_없다() {
        int boardId = 1;
        User wrongUser = user2;

        if ( !boardService.checkWriterId(boardId, wrongUser) ) {
            throw new NoRightOfModifyAndDeleteException("현재 유저와 게시글의 작성자가 일치하지 않습니다.");
        }
    }

    @Test(expected = NoRightOfModifyAndDeleteException.class)
    public void 게시글의_비밀번호가_일치하지_않으면_수정_및_삭제_권한이_없다() {
        int boardId = 1;
        User rightUser = user1;

        if ( boardService.checkWriterId(boardId, rightUser) ) {
            String wrongPassword = "틀린 패스워드";

            if ( !boardService.checkPassword(boardId, wrongPassword) ) {
                throw new NoRightOfModifyAndDeleteException("게시글의 비밀번호가 일치하지 않습니다.");
            }
        }
    }

    @Test
    public void 번개모임에_참여한다() {
        int boardId = 1;
        int currentUserCount = boardService.getBoardById(boardId).getUserList().size();
        User joinUser = new User("참가 유저", "참가 암호", "참가 이름", "참가 이메일", Gender.FEMAIL);

        boardService.joinBoard(boardId, joinUser);

        assertEquals(boardService.getBoardById(boardId).getUserList().size(), currentUserCount + 1);

        List<User> joinUserList = boardService.getAllUserInBoard(boardId);
        checkAllValueOfUser(joinUserList.get(joinUserList.size() - 1), joinUser);
    }

    
    @Test(expected = MaxUserOverflowInBoardException.class)
    public void 번개모임의_최대_참여인원을_넘으면_참여_할수없다() {
        int boardId = 1;

        User joinUser1 = new User("참가 유저1", "참가 암호1", "참가 이름1", "참가 이메일1", Gender.MALE);
        User joinUser2 = new User("참가 유저2", "참가 암호2", "참가 이름2", "참가 이메일2", Gender.FEMAIL);
        User joinUser3 = new User("참가 유저3", "참가 암호3", "참가 이름3", "참가 이메일3", Gender.MALE);
        User joinUser4 = new User("참가 유저4", "참가 암호4", "참가 이름4", "참가 이메일4", Gender.FEMAIL);
        User joinUser5 = new User("참가 유저5", "참가 암호5", "참가 이름5", "참가 이메일5", Gender.MALE);

        boardService.joinBoard(boardId, joinUser1);
        boardService.joinBoard(boardId, joinUser2);
        boardService.joinBoard(boardId, joinUser3);
        boardService.joinBoard(boardId, joinUser4);
        boardService.joinBoard(boardId, joinUser5);
    }

    @Test(expected = AlreadyJoinUserException.class)
    public void 같은_번개모임에_중복으로_참여_할수없다() {
        int boardId = 1;
        User joinUser = new User("참가 유저", "참가 암호", "참가 이름", "참가 이메일", Gender.FEMAIL);

        boardService.joinBoard(boardId, joinUser);
        boardService.joinBoard(boardId, joinUser);

    }

    private void checkAllValueOfBoard(Board board1, Board board2) {
        assertEquals(board1.getTitle(), board2.getTitle());
        assertEquals(board1.getWriter(), board2.getWriter());
        assertEquals(board1.getPassword(), board2.getPassword());
        assertEquals(board1.getImage(), board2.getImage());
        assertEquals(board1.getContent(), board2.getContent());
    }

    private void checkAllValueOfUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getPassword(), user2.getPassword());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(user1.getGender(), user2.getGender());
    }
}

