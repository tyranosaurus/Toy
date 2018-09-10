package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.dto.User;

import java.util.List;

public interface RepositoryInterface {
    List<Board> getAll();
    Board getByNo(int no);
    int add(Board board);
    int getCount();
    void update(int boardNo, String title, String image, String content);
    void delete(int boardNo);
    void deleteAll();
    void addUserNoIntoBoard(int boardNo, int joinUserNo);
    List<Integer> getAllUser(int boardNo);
    int getUserCount(int boardNo);
    int deleteUserAtBoard(int boardNo, int userNo);
    int hasUserNoAtBoard(int boardNo, int userNo);
}
