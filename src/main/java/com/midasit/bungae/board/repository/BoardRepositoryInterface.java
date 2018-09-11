package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;

import java.util.List;

public interface BoardRepositoryInterface {
    List<Board> getAll();
    Board getByNo(int no);
    int add(Board board);
    int getCount();
    void update(int boardNo, String title, String image, String content);
    void delete(int boardNo);
    void deleteAll();
}
