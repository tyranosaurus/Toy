package com.midasit.bungae.boarddetail.service;

import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.repository.BoardDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardDetailServiceImpl implements BoardDetailService {
    @Autowired
    BoardDetailRepository boardDetailRepository;

    @Override
    public BoardDetail get(int boardNo) {
        return boardDetailRepository.get(boardNo);
    }
}
