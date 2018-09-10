package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.dto.User;

import java.util.List;

public interface Repository {
    List<Board> getAll();
    Board getById(int id);
    void add(Board board);
    int getCount();
    void update(int boardId, String title, String image, String content);
    int delete(int boardId);
    void addUserInBoard(int boardId, User joinUser);
    List<User> getAllUser(int boardId);

}
