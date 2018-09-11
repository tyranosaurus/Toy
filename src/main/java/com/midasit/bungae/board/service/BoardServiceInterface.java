package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.dto.User;

import java.util.List;

public interface BoardServiceInterface {
    List<Board> getAllBoard();
    Board getBoardByNo(int no);
    int createNew(Board board);
    int getBoardCount();
    boolean isEqualWriter(int boardNo, User loginUser);
    boolean isEqualPassword(int boardNo, String rightPassword);
    void modifyBoard(int boardNo, String title, String image, String content);
    void deleteBoard(int boardNo);
    void deleteAll();
    int getUserCountOfBoard(int boardNo);
    void joinUserAtBoard(int boardNo, int joinUserNo);
    List<Integer> getUserNoListInBoard(int boardNo);
    void cancelJoinFromBoard(int boardNo, int userNo);
}
