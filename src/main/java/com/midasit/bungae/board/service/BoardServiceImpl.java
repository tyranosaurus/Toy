package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.AlreadyParticipantException;
import com.midasit.bungae.board.exception.MaxParticipantOverflowInBoardException;
import com.midasit.bungae.board.exception.NoParticipantException;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.repository.BoardDetailRepository;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.board.exception.MaxBoardOverflowException;
import com.midasit.bungae.board.repository.BoardRepository;
import com.midasit.bungae.boarduser.repository.BoardUserRepository;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private static final int MAX_BOARD_COUNT = 5;

    /* 네이밍 수정할 것 */
    @Autowired
    BoardRepository boardDao;
    @Autowired
    BoardUserRepository boardUserDao;
    @Autowired
    UserRepository userDao;
    @Autowired
    BoardDetailRepository boardDetailDao;

    public BoardServiceImpl() { }

    @Override
    public Board get(int no) {
        return boardDao.get(no);
    }

    @Override
    public List<Board> getAll() {
        return boardDao.getAll();
    }

    @Override
    public BoardDetail getDetail(int boardNo) {
        return boardDetailDao.get(boardNo);
    }

    @Override
    public int createNew(Board board) {
        if ( getCount() < MAX_BOARD_COUNT ) {
            int boardNo = boardDao.add(board);
            boardUserDao.add(boardNo, board.getWriter().getNo());

            return boardNo;
        } else {
            throw new MaxBoardOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    @Override
    public int getCount() {
        return boardDao.getCount();
    }

    @Override
    public boolean isEqualWriter(int boardNo, User loginUser) {
        Board board = boardDao.get(boardNo);

        if ( board.getWriter().getNo() == loginUser.getNo() ) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isEqualPassword(int boardNo, String rightPassword) {
        Board board = boardDao.get(boardNo);

        if ( board.getPassword().equals(rightPassword) ) {
            return true;
        }

        return false;
    }

    @Override
    public void modify(int boardNo, String title, String image, String content) {
        boardDao.update(boardNo, title, image, content);
    }

    @Override
    public void delete(int boardNo) {
        // board_user 테이블 삭제
        boardUserDao.deleteBoard(boardNo);
        // board 테이블 삭제
        boardDao.delete(boardNo);
    }

    @Override
    public void deleteAll() {
        // board_user 테이블 삭제
        boardUserDao.deleteAll();
        // board 테이블 삭제
        boardDao.deleteAll();
    }

    @Override
    public int getParticipantCount(int boardNo) {
        return boardUserDao.getParticipantCount(boardNo);
    }

    @Override
    public List<User> getParticipants(int boardNo) {
        List<User> participants = new ArrayList<>();
        List<Integer> participantNoList = boardUserDao.getParticipantNoList(boardNo);

        for ( int i = 0; i < participantNoList.size(); i++ ) {
            participants.add(userDao.get(participantNoList.get(i)));
        }

        return participants;
    }

    @Override
    public void participate(int boardNo, int participantNo) {
        if ( getParticipantCount(boardNo) < boardDao.get(boardNo).getMaxParticipantCount() ) {
            if ( hasSameUser(boardNo, participantNo) ) {
                boardUserDao.addParticipant(boardNo, participantNo);
            } else {
                throw new AlreadyParticipantException("이미 현재 번개모임에 참여하였습니다.");
            }
        } else {
            throw new MaxParticipantOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

    @Override
    public void cancelParticipation(int boardNo, int userNo) {
        if ( boardUserDao.hasParticipant(boardNo, userNo) == 1) {
            boardUserDao.deleteParticipant(boardNo, userNo);
        } else {
            throw new NoParticipantException("번개모임에 참여하지 않았습니다.");
        }
    }

    private boolean hasSameUser(int boardNo, int participantNo) {
        List<Integer> participantNoList = boardUserDao.getParticipantNoList(boardNo);

        for ( int i = 0; i < participantNoList.size(); i++ ) {
            if ( participantNoList.get(i) == participantNo ) {
                return false;
            }
        }

        return true;
    }
}
