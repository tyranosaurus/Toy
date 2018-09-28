package com.midasit.bungae.boarddetail.service;

import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.boarddetail.repository.BoardDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardDetailServiceImpl implements BoardDetailService {
    @Autowired
    BoardDetailRepository boardDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public BoardDetail get(int boardNo) {
        return boardDetailRepository.get(boardNo);
    }
}
