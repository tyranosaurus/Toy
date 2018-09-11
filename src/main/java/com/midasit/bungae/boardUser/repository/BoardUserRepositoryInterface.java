package com.midasit.bungae.boardUser.repository;

import java.util.List;

public interface BoardUserRepositoryInterface {
    int add(int boardNo, int userNo);
    void delete(int boardNo);
    void deleteAll();
    int getUserCountAtBoard(int boardNo);
    void addUserNoToBoard(int boardNo, int joinUserNo);
    List<Integer> getAllUserAtBoard(int boardNo);
    void deleteUserAtBoard(int boardNo, int userNo);
    int hasUserNoAtBoard(int boardNo, int userNo);
}
