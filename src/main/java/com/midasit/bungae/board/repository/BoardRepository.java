package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;

import java.util.List;

public interface BoardRepository {
    List<Board> getAll();
    Board get(int no);
    int add(Board board);
    int getCount();
    void update(Board board);
    void delete(int boardNo);
    void deleteAll();
}
