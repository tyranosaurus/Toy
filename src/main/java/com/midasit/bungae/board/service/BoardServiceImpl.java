package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.exception.*;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.repository.BoardDetailRepository;
import com.midasit.bungae.user.dto.User;
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

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardUserRepository boardUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardDetailRepository boardDetailRepository;

    public BoardServiceImpl() { }

    @Override
    public Board get(int no) {
        return boardRepository.get(no);
    }

    @Override
    public List<Board> getAll() {
        return boardRepository.getAll();
    }

    @Override
    public int createNew(Board board) {
        if ( getCount() < MAX_BOARD_COUNT ) {
            int boardNo = boardRepository.add(board);
            boardUserRepository.add(boardNo, board.getWriter().getNo());

            return boardNo;
        } else {
            throw new MaxBoardOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    @Override
    public int getCount() {
        return boardRepository.getCount();
    }

    @Override
    public boolean isEqualWriter(int boardNo, int writerNo) {
        Board board = boardRepository.get(boardNo);
        User writer = userRepository.get(writerNo);

        if ( board.getWriter().getNo() == writer.getNo() ) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isEqualPassword(int boardNo, String password) {
        Board board = boardRepository.get(boardNo);

        if ( board.getPassword().equals(password) ) {
            return true;
        }

        return false;
    }

    @Override
    public void modify(int boardNo, String title, String image, String content, int maxParticipantCount, String password, int writerNo) {
        if ( isEqualWriter(boardNo, writerNo) ) {
            if ( isEqualPassword(boardNo, password) ) {
                boardRepository.update(boardNo, title, null, content, maxParticipantCount, password);
            }
        }
    }

    @Override
    public void delete(int boardNo, String password, int writerNo) {
        if ( isEqualWriter(boardNo, writerNo) ) {
            if ( isEqualPassword(boardNo, password) ) {
                // board_user 테이블 삭제
                boardUserRepository.deleteBoard(boardNo);
                // board 테이블 삭제
                boardRepository.delete(boardNo);
            } else {
                throw new NotEqualPasswordException("게시판의 비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new NotEqualWriterException("작성자가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteAll() {
        // board_user 테이블 삭제
        boardUserRepository.deleteAll();
        // board 테이블 삭제
        boardRepository.deleteAll();
    }

    @Override
    public int getParticipantCount(int boardNo) {
        return boardUserRepository.getParticipantCount(boardNo);
    }

    @Override
    public List<User> getParticipants(int boardNo) {
        List<User> participants = new ArrayList<>();
        List<Integer> participantNoList = boardUserRepository.getParticipantNoList(boardNo);

        for ( int i = 0; i < participantNoList.size(); i++ ) {
            participants.add(userRepository.get(participantNoList.get(i)));
        }

        return participants;
    }

    @Override
    public void participate(int boardNo, int participantNo) {
        if ( getParticipantCount(boardNo) < boardRepository.get(boardNo).getMaxParticipantCount() ) {
            if ( hasSameUser(boardNo, participantNo) ) {
                boardUserRepository.addParticipant(boardNo, participantNo);
            } else {
                throw new AlreadyParticipantException("이미 현재 번개모임에 참여하였습니다.");
            }
        } else {
            throw new MaxParticipantOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

    @Override
    public void cancelParticipation(int boardNo, int userNo) {
        if ( boardUserRepository.hasParticipant(boardNo, userNo) == 1) {
            boardUserRepository.deleteParticipant(boardNo, userNo);
        } else {
            throw new NoParticipantException("번개모임에 참여하지 않았습니다.");
        }
    }

    private boolean hasSameUser(int boardNo, int participantNo) {
        List<Integer> participantNoList = boardUserRepository.getParticipantNoList(boardNo);

        for ( int i = 0; i < participantNoList.size(); i++ ) {
            if ( participantNoList.get(i) == participantNo ) {
                return false;
            }
        }

        return true;
    }
}
