package com.midasit.bungae.boarddetail.repository;

import com.midasit.bungae.board.repository.BoardRepository;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarduser.repository.BoardUserRepository;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDetailDao implements BoardDetailRepository {
    @Autowired
    BoardRepository boardDao;
    @Autowired
    BoardUserRepository boardUserDao;
    @Autowired
    UserRepository userDao;

    @Override
    public BoardDetail get(int boardNo) {
        BoardDetail boardDetail = new BoardDetail(boardDao.get(boardNo));

        List<Integer> participantNoList = boardUserDao.getParticipantNoList(boardDao.get(boardNo).getNo());

        for ( int i = 0; i < participantNoList.size(); i++ ) {
            boardDetail.addParticipant(userDao.get(participantNoList.get(i)));
        }

        return boardDetail;
    }
}
