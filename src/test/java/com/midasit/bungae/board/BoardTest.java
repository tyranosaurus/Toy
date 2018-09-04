package com.midasit.bungae.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void 정상적으로_게시물을_작성한다() {
        BoardService boardService = new BoardService();

        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());

        assertEquals(5, boardService.getNewBoards().size());
    }

    @Test(expected = MaxArticleOverflowException.class)
    public void 번개신청_게시물은_5개까지만_등록할_수_있다() {
        BoardService boardService = new BoardService();

        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());
        boardService.createNew(new Board());
    }

    @Test
    public void 게시물_삭제는_작성자만_가능하다() {

    }
}
