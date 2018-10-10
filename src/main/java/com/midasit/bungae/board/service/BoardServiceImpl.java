package com.midasit.bungae.board.service;

import com.midasit.bungae.admin.dto.Notice;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.repository.BoardRepository;
import com.midasit.bungae.boarddetail.repository.BoardDetailRepository;
import com.midasit.bungae.boarduser.repository.BoardUserRepository;
import com.midasit.bungae.exception.*;
import com.midasit.bungae.notice.repository.NoticeRepository;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
    @Autowired
    NoticeRepository noticeRepository;

    public BoardServiceImpl() { }

    @Override
    @Transactional(readOnly = true)
    public Board get(int no) {
        return boardRepository.get(no);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> getAll() {
        return boardRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notice> getNotices() {
        return noticeRepository.getAll();
    }

    @Override
    public Notice getNotice(int noticeNo) {
        return noticeRepository.get(noticeNo);
    }

    @Override
    public int createNew(Board board) {
        //checkPassword();
        if ( board.getTitle() == null || board.getTitle().length() < 1
                || board.getPassword() == null || board.getPassword().length() < 1
                || board.getContent() == null || board.getContent().length() < 1
                || board.getMaxParticipantCount() < 1) {
            throw new EmptyValueOfBoardCreationException("값이 없는 정보가 있습니다. 모든 칸을 채워주세요.");
        }

        if ( getCount() < MAX_BOARD_COUNT ) {
            int boardNo = boardRepository.add(board);
            boardUserRepository.add(boardNo, board.getWriter().getNo());

            return boardNo;
        } else {
            throw new MaxBoardOverflowException("최대 게시글 수를 초과하였습니다.");
        }
    }

    @Override
    public int createNotice(Notice notice) {
        if ( notice.getTitle() == null || notice.getTitle().length() < 1
                || notice.getPassword() == null || notice.getPassword().length() < 1
                || notice.getContent() == null || notice.getContent().length() < 1 ) {
            throw new EmptyValueOfNoticeCreationException("값이 없는 정보가 있습니다. 모든 칸을 채워주세요.");
        }

        return noticeRepository.add(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public int getCount() {
        return boardRepository.getCount();
    }

    @Override
    public boolean isEqualWriter(int boardNo, int userNo) {
        Board board = boardRepository.get(boardNo);
        User writer = userRepository.get(userNo);

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
    public void modify(int boardNo, String title, String image, String content, int maxParticipantCount, String password, int userNo) {
        if ( userRepository.getAuthority(userNo).equals("ROLE_ADMIN") ) {
            boardRepository.update(new Board(boardNo, title, null, null, content, maxParticipantCount, null));
            return;
        }

        if ( isEqualWriter(boardNo, userNo) ) {
            if ( isEqualPassword(boardNo, password) ) {
                boardRepository.update(new Board(boardNo, title, password, null, content, maxParticipantCount, null));
            } else {
                throw new NotEqualPasswordException("게시판의 비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new NotEqualWriterException("작성자가 일치하지 않습니다.");
        }
    }

    @Override
    public void modifyNotice(int noticeNo, String title, String image, String content, String password, int writerNo) {
        if ( isEqualWriterAtNotice(noticeNo, writerNo) ) {
            if ( isEqualPasswordAtNotice(noticeNo, password) ) {
                noticeRepository.update(new Notice(noticeNo, title, password, null, content, null));
            } else {
                throw new NotEqualPasswordException("공지사항의 비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new NotEqualWriterException("작성자가 일치하지 않습니다.");
        }
    }

    private boolean isEqualWriterAtNotice(int noticeNo, int writerNo) {
        Notice notice = noticeRepository.get(noticeNo);
        User writer = userRepository.get(writerNo);

        if ( notice.getWriter().getNo() == writer.getNo() ) {
            return true;
        }

        return false;
    }

    private boolean isEqualPasswordAtNotice(int noticeNo, String password) {
        Notice notice = noticeRepository.get(noticeNo);

        if ( notice.getPassword().equals(password) ) {
            return true;
        }

        return false;
    }

    @Override
    public void delete(int boardNo, String password, int userNo) {
        if ( isEqualWriter(boardNo, userNo) ) {
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
    public void deleteNotice(Integer noticeNo, String password, int writerNo) {
        if ( isEqualWriterAtNotice(noticeNo, writerNo) ) {
            if ( isEqualPasswordAtNotice(noticeNo, password) ) {
                noticeRepository.delete(noticeNo);
            } else {
                throw new NotEqualPasswordException("게시판의 비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new NotEqualWriterException("작성자가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteByAdmin(Integer boardNo, String authority) {
        if ( authority.equals("ROLE_ADMIN") ) {
            boardUserRepository.deleteBoard(boardNo);
            boardRepository.delete(boardNo);

            return;
        }

        throw new NotAdminAccountException();
    }

    @Override
    @Transactional(readOnly = true)
    public int getParticipantCount(int boardNo) {
        return boardUserRepository.getParticipantCount(boardNo);
    }

    @Override
    @Transactional(readOnly = true)
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
                throw new AlreadyParticipantException("이미 번개모임에 참여하였습니다.");
            }
        } else {
            throw new MaxParticipantOverflowInBoardException("최대 참가자 수를 초과하였습니다.");
        }
    }

    @Override
    public void cancelParticipation(int boardNo, int userNo) {
        if ( boardUserRepository.hasParticipant(boardNo, userNo) == 1 ) {
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
