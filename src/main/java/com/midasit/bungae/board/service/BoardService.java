package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dao.BoardDao;
import com.midasit.bungae.board.dao.User;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.AlreadyJoinUserException;
import com.midasit.bungae.board.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.exception.MaxUserOverflowInBoardException;

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
            throw new MaxBoardOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    public int getBoardCount() {
        return boardDao.getCount();
    }

    public boolean checkWriterId(int boardId, User loginUser) {
        Board board = boardDao.getById(boardId);

        if ( board.getWriter().getId().equals(loginUser.getId()) ) {
            return true;
        }

        return false;
    }

    public boolean checkPassword(int boardId, String rightPassword) {
        Board board = boardDao.getById(boardId);

        if ( board.getPassword().equals(rightPassword) ) {
            return true;
        }

        return false;
    }

    public void modifyBoard(int boardId, String title, String image, String content) {
        boardDao.update(boardId, title, image, content);
    }

    public int deleteBoard(int boardId) {
        return boardDao.delete(boardId);
    }

    public void joinBoard(int boardId, User joinUser) {
        Board board = boardDao.getById(boardId);
        int currentUserCount = board.getUserList().size();

        // 참가 중복 체크
        for ( int i = 0; i < board.getUserList().size(); i++ ) {
            if ( board.getUserList().get(i).getId().equals(joinUser.getId()) ) {
                throw new AlreadyJoinUserException("이미 현재 번개모임에 참여하였습니다.");
            }
        }

        // 최대 참가자 수 체크
        if ( currentUserCount < board.getMaxUserCount() ) {
            boardDao.addUserInBoard(boardId, joinUser);
        } else {
            throw new MaxUserOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

    public List<User> getAllUserInBoard(int boardId) {
        return boardDao.getAllUser(boardId);
    }
}
