package com.midasit.bungae.board.service;

import com.midasit.bungae.board.repository.BoardMemoryRepository;
import com.midasit.bungae.board.dto.User;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.AlreadyJoinUserException;
import com.midasit.bungae.board.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.exception.MaxUserOverflowInBoardException;
import com.midasit.bungae.board.exception.NoJoinUserException;

import java.util.List;

 class BoardService {

    private static final int MAX_BOARD_COUNT = 5;

    BoardMemoryRepository boardMemoryRepository = null;

    BoardService() {
        boardMemoryRepository = new BoardMemoryRepository();
    }

    List<Board> getAllBoard() {
        return boardMemoryRepository.getAll();
    }

    Board getBoardById(int id) {
        return boardMemoryRepository.getById(id);
    }

    void createNew(Board board) {
        if ( getBoardCount() < MAX_BOARD_COUNT ) {
            boardMemoryRepository.add(board);
        } else {
            throw new MaxBoardOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    int getBoardCount() {
        return boardMemoryRepository.getCount();
    }

    boolean isEqualWriterId(int boardId, User loginUser) {
        Board board = boardMemoryRepository.getById(boardId);

        if ( board.getWriter().getId().equals(loginUser.getId()) ) {
            return true;
        }

        return false;
    }

    boolean isEqualPassword(int boardId, String rightPassword) {
        Board board = boardMemoryRepository.getById(boardId);

        if ( board.getPassword().equals(rightPassword) ) {
            return true;
        }

        return false;
    }

     void modifyBoard(int boardId, String title, String image, String content) {
        boardMemoryRepository.update(boardId, title, image, content);
    }

     int deleteBoard(int boardId) {
        return boardMemoryRepository.delete(boardId);
    }

    void joinUserAtBoard(int boardId, User joinUser) {
        Board board = boardMemoryRepository.getById(boardId);

        if ( board.getUserList().size() < board.getMaxUserCount() ) {
            if ( hasSameUser(board, joinUser) ) {
                boardMemoryRepository.addUserInBoard(boardId, joinUser);
            } else {
                throw new AlreadyJoinUserException("이미 현재 번개모임에 참여하였습니다.");
            }
        } else {
            throw new MaxUserOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

     List<User> getAllUserInBoard(int boardId) {
        return boardMemoryRepository.getAllUser(boardId);
    }

     String cancelJoinFromBoard(int boardId, String cancelUserId) {
        Board board = boardMemoryRepository.getById(boardId);
        List<User> userList = board.getUserList();

        for ( int i = 0; i < userList.size(); i++ ) {
            if ( userList.get(i).getId().equals(cancelUserId) ) {
                return userList.remove(i).getId();
            }
        }

        throw new NoJoinUserException("번개모임에 참여하지 않았습니다.");
    }

    private boolean hasSameUser(Board board, User joinUser) {
        for ( int i = 0; i < board.getUserList().size(); i++ ) {
            if ( board.getUserList().get(i).getId().equals(joinUser.getId()) ) {
                return false;
            }
        }

        return true;
    }
}
