package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.dto.User;
import com.midasit.bungae.board.exception.AlreadyJoinUserException;
import com.midasit.bungae.board.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.exception.MaxUserOverflowInBoardException;
import com.midasit.bungae.board.exception.NoJoinUserException;
import com.midasit.bungae.board.repository.BoardRepositoryInterface;
import com.midasit.bungae.boardUser.repository.BoardUserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardServiceInterface {
    @Autowired
    BoardRepositoryInterface boardDaoRepository;
    @Autowired
    BoardUserRepositoryInterface boardUserDaoRepository;

    private static final int MAX_BOARD_COUNT = 5;

    public BoardServiceImpl() { }

    public List<Board> getAllBoard() {
        return boardDaoRepository.getAll();
    }

    public Board getBoardByNo(int no) {
        return boardDaoRepository.getByNo(no);
    }

    public int createNew(Board board) {
        if ( getBoardCount() < MAX_BOARD_COUNT ) {
            int boardNo = boardDaoRepository.add(board);
            boardUserDaoRepository.add(boardNo, board.getUserNo());

            return boardNo;
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
        /**외래키 지정은 x, 이런 방식으로 진행, DB 논리모델시에는 관계 맺어줌.*/
        // board 테이블 삭제
        boardDaoRepository.delete(boardNo);
        // board_user 테이블 삭제
        boardUserDaoRepository.delete(boardNo);
    }

    public void deleteAll() {
        // board 테이블 삭제
        boardDaoRepository.deleteAll();
        // board_user 테이블 삭제
        boardUserDaoRepository.deleteAll();
    }

    public int getUserCountOfBoard(int boardNo) {
        return boardUserDaoRepository.getUserCountAtBoard(boardNo);
    }

    public void joinUserAtBoard(int boardNo, int joinUserNo) {
        if ( getUserCountOfBoard(boardNo) < boardDaoRepository.getByNo(boardNo).getMaxUserCount() ) {
            if ( hasSameUser(boardNo, joinUserNo) ) {
                boardUserDaoRepository.addUserNoToBoard(boardNo, joinUserNo);
            } else {
                throw new AlreadyJoinUserException("이미 현재 번개모임에 참여하였습니다.");
            }
        } else {
            throw new MaxUserOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

    public List<Integer> getUserNoListInBoard(int boardNo) {
        return boardUserDaoRepository.getAllUserAtBoard(boardNo);
    }

    public void cancelJoinFromBoard(int boardNo, int userNo) {
        if ( boardUserDaoRepository.hasUserNoAtBoard(boardNo, userNo) == 1) {
            boardUserDaoRepository.deleteUserAtBoard(boardNo, userNo);
        } else {
            throw new NoJoinUserException("번개모임에 참여하지 않았습니다.");
        }
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
