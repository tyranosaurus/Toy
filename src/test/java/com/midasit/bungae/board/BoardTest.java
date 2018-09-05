package com.midasit.bungae.board;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.MaxArticleOverflowException;
import com.midasit.bungae.board.exception.WrongBoardPasswordException;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    BoardService boardService = null;
    List<Board> boardList = null;

    @Before
    public void setUp() throws Exception {
        boardService = new BoardService();

        boardList = Arrays.asList(new Board(1, "타이틀1", "작성자1", "패스워드1", "사진1", "비밀번호1"),
                new Board(2, "타이틀2", "작성자2", "패스워드2", "사진2", "비밀번호2"),
                new Board(3, "타이틀3", "작성자3", "패스워드3", "사진3", "비밀번호3"));

        for ( int i = 0; i < boardList.size(); i++ ) {
            boardService.createNew(boardList.get(i));
        }
    }

    @Test
    public void 모든_게시물을_가져온다() {
        List<Board> allBoards = boardService.getAllBoard();
        assertEquals(boardService.getBoardCount(), boardList.size());

        for ( int i = 0; i < boardService.getBoardCount(); i++ ) {
            checkAllValueEqualable(allBoards.get(i), boardList.get(i));
        }
    }

    @Test
    public void 새_게시물을_작성한다() {
        Board newBoard1 = new Board(1, "타이틀1", "작성자1", "패스워드1", "사진1", "비밀번호1");
        Board newBoard2 = new Board(2, "타이틀2", "작성자2", "패스워드2", "사진2", "비밀번호2");

        boardService.createNew(newBoard1);
        Board getBoard1 = boardService.getBoardById(newBoard1.getId());

        checkAllValueEqualable(newBoard1, getBoard1);

        boardService.createNew(newBoard2);
        Board getBoard2 = boardService.getBoardById(newBoard2.getId());

        checkAllValueEqualable(newBoard2, getBoard2);
    }

    @Test(expected = MaxArticleOverflowException.class)
    public void 게시물은_최대_5개까지만_등록할_수_있다() {
        boardService.createNew(new Board(4, "타이틀4", "작성자4", "패스워드4", "사진4", "비밀번호4"));
        boardService.createNew(new Board(5, "타이틀5", "작성자5", "패스워드5", "사진5", "비밀번호5"));
        boardService.createNew(new Board(6, "타이틀6", "작성자6", "패스워드6", "사진6", "비밀번호6"));
    }

    @Test
    public void 비밀번호가_일치해야_게시물_수정이_가능하다() {
        Board modifiedBoard = new Board(2, "타이틀2", "작성자2", "패스워드2", "사진2", "비밀번호2");
        String password = "패스워드2";

        modifiedBoard.setTitle("수정 타이틀");
        modifiedBoard.setWriter("수정 작성자");
        modifiedBoard.setImage("수정 사진");
        modifiedBoard.setContent("수정 내용");

        boardService.modifyBoard(modifiedBoard, password);

        Board getBoard = boardService.getBoardById(modifiedBoard.getId());

        checkAllValueEqualable(modifiedBoard, getBoard);
    }

    @Test(expected = WrongBoardPasswordException.class)
    public void 비밀번호가_틀리면_게시물_수정이_안된다() {
        Board modifiedBoard = new Board(2, "타이틀2", "작성자2", "패스워드2", "사진2", "비밀번호2");
        String password = "틀린 패스워드";

        modifiedBoard.setTitle("수정 타이틀");
        modifiedBoard.setWriter("수정 작성자");
        modifiedBoard.setImage("수정 사진");
        modifiedBoard.setContent("수정 내용");

        boardService.modifyBoard(modifiedBoard, password);

        Board getBoard = boardService.getBoardById(modifiedBoard.getId());

        checkAllValueEqualable(modifiedBoard, getBoard);
    }

    @Test
    public void 비밀번호가_일치해야_게시물_삭제가_가능하다() {
        Board deletedBoard = new Board(3, "타이틀3", "작성자3", "패스워드3", "사진3", "비밀번호3");
        String password = "패스워드3";

        int deletedId = boardService.deleteBoard(deletedBoard, password);

        assertEquals(boardService.getBoardCount(), 2);
        assertEquals(deletedBoard.getId(), deletedId);
    }

    @Test(expected = WrongBoardPasswordException.class)
    public void 비밀번호가_틀리면_게시물_삭제가_안된다() {
        Board deletedBoard = new Board(3, "타이틀3", "작성자3", "패스워드3", "사진3", "비밀번호3");
        String password = "틀린 패스워드";

        int deletedId = boardService.deleteBoard(deletedBoard, password);

        assertEquals(boardService.getBoardCount(), 2);
        assertEquals(deletedBoard.getId(), deletedId);
    }

    private void checkAllValueEqualable(Board board1, Board board2) {
        assertEquals(board1.getTitle(), board2.getTitle());
        assertEquals(board1.getWriter(), board2.getWriter());
        assertEquals(board1.getPassword(), board2.getPassword());
        assertEquals(board1.getImage(), board2.getImage());
        assertEquals(board1.getContent(), board2.getContent());
    }
}

