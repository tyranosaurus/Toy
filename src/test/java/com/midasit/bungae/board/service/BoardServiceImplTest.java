package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.repository.BoardRepository;
import com.midasit.bungae.boarduser.repository.BoardUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceImplTest {

    @InjectMocks
    private final BoardServiceImpl boardService = new BoardServiceImpl();

    @Mock
    BoardRepository boardDaoRepository;

    @Mock
    BoardUserRepository boardUserDaoRepository;

    @Test
    public void createNew_success() {
        when(boardDaoRepository.getCount()).thenReturn(4);

        Board board = new Board();
        boardService.createNew(board);

        verify(boardDaoRepository, times(1)).add(any(Board.class));
        verify(boardUserDaoRepository, times(1)).add(anyInt(), anyInt());
    }

    @Test(expected = MaxBoardOverflowException.class)
    public void createNew_fail() {
        when(boardDaoRepository.getCount()).thenReturn(5);

        Board board = new Board();
        boardService.createNew(board);
    }
}
