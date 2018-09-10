package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.dto.User;
import com.midasit.bungae.board.exception.AlreadyJoinUserException;
import com.midasit.bungae.board.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.exception.MaxUserOverflowInBoardException;
import com.midasit.bungae.board.exception.NoJoinUserException;
import com.midasit.bungae.board.repository.BoardDaoRepository;
import com.midasit.bungae.board.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardDaoRepository boardDaoRepository;
    //RepositoryInterface boardDaoRepository;


    private static final int MAX_BOARD_COUNT = 5;

    public BoardService() { }

    public List<Board> getAllBoard() {
        return boardDaoRepository.getAll();
    }

    public Board getBoardByNo(int no) {
        return boardDaoRepository.getByNo(no);
    }

    public int createNew(Board board) {
        if ( getBoardCount() < MAX_BOARD_COUNT ) {
            return boardDaoRepository.add(board);
        } else {
            throw new MaxBoardOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    public int getBoardCount() {
        return boardDaoRepository.getCount();
    }

    public boolean isEqualWriter(int boardNo, User loginUser) {
        Board board = boardDaoRepository.getByNo(boardNo);

        if ( board.getUserNo() == loginUser.getNo() ) {
            return true;
        }

        return false;
    }

    public boolean isEqualPassword(int boardNo, String rightPassword) {
        Board board = boardDaoRepository.getByNo(boardNo);

        if ( board.getPassword().equals(rightPassword) ) {
            return true;
        }

        return false;
    }

    public void modifyBoard(int boardNo, String title, String image, String content) {
        boardDaoRepository.update(boardNo, title, image, content);
    }

    public void deleteBoard(int boardNo) {
        boardDaoRepository.delete(boardNo);
    }

    public void deleteAll() {
        boardDaoRepository.deleteAll();
    }

    public int getUserCountOfBoard(int boardNo) {
        return boardDaoRepository.getUserCount(boardNo);
    }

    public void joinUserAtBoard(int boardNo, int joinUserNo) {
        if ( getUserCountOfBoard(boardNo) < boardDaoRepository.getByNo(boardNo).getMaxUserCount() ) {
            if ( hasSameUser(boardNo, joinUserNo) ) {
                boardDaoRepository.addUserNoIntoBoard(boardNo, joinUserNo);
            } else {
                throw new AlreadyJoinUserException("이미 현재 번개모임에 참여하였습니다.");
            }
        } else {
            throw new MaxUserOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

    public List<Integer> getUserNoListInBoard(int boardNo) {
        return boardDaoRepository.getAllUser(boardNo);
    }

    public int cancelJoinFromBoard(int boardNo, int userNo) {
        if ( boardDaoRepository.hasUserNoAtBoard(boardNo, userNo) == 1) {
            return boardDaoRepository.deleteUserAtBoard(boardNo, userNo);
        }

        throw new NoJoinUserException("번개모임에 참여하지 않았습니다.");
    }

    private boolean hasSameUser(int boardNo, int joinUserNo) {
        List<Integer> userNoList = getUserNoListInBoard(boardNo);

        for ( int i = 0; i < userNoList.size(); i++ ) {
            if ( userNoList.get(i) == joinUserNo ) {
                return false;
            }
        }

        return true;
    }
}
