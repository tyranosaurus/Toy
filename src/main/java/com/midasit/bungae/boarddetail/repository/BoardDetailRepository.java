package com.midasit.bungae.boarddetail.repository;

import com.midasit.bungae.boarddetail.dto.BoardDetail;

public interface BoardDetailRepository {
    BoardDetail get(int boardNo);
}
