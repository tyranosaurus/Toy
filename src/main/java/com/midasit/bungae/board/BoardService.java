package com.midasit.bungae.board;

import com.midasit.bungae.board.dao.BoardDao;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.MaxArticleOverflowException;
import com.midasit.bungae.board.exception.WrongBoardPasswordException;

import java.util.List;

public class BoardService {

    private static final int MAX_BOARD_COUNT = 5;

    BoardDao boardDao = null;

    public BoardService() {
        boardDao = new BoardDao();
    }

    public List<Board> getAllBoard() {
        return boardDao.getAll();
    }

    public Board getBoardById(int id) {
        return boardDao.getById(id);
    }

    public void createNew(Board board) {
        if ( getBoardCount() < MAX_BOARD_COUNT ) {
            boardDao.add(board);
        } else {
            throw new MaxArticleOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    public int getBoardCount() {
        return boardDao.getCount();
    }

    public void modifyBoard(Board modifiedBoard, String password) {
        if ( isEqualPassword(modifiedBoard, password) ) {
            boardDao.update(modifiedBoard);
        } else {
            throw new WrongBoardPasswordException("게시글의 비밀번호가 일치하지 않습니다.");
        }
    }

    public int deleteBoard(Board deletedBoard, String password) {
        if ( isEqualPassword(deletedBoard, password) ) {
            int deletedId = boardDao.delete(deletedBoard);

            return deletedId;
        } else {
            throw new WrongBoardPasswordException("게시글의 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean isEqualPassword(Board modifiedBoard, String password) {
        Board originalBoard = boardDao.getById(modifiedBoard.getId());

        if ( originalBoard.getPassword().equals(password) ) {
            return true;
        }

        return false;
    }
}
