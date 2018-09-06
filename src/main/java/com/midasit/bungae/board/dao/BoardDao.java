package com.midasit.bungae.board.dao;

import com.midasit.bungae.board.dto.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardDao {
    List<Board> boardList = null;

    public BoardDao() {
        boardList = new ArrayList<Board>();
    }

    public List<Board> getAll() {
        return boardList;
    }

    public Board getById(int id) {
        return boardList.get(getIndex(id));
    }

    public void add(Board board) {
        boardList.add(board);
    }

    public int getCount() {
        return boardList.size();
    }

    public void update(int boardId, String title, String image, String content) {
        Board modifiedBoard = boardList.get(getIndex(boardId));

        modifiedBoard.setTitle(title);
        modifiedBoard.setImage(image);
        modifiedBoard.setContent(content);
    }

    public int delete(int boardId) {
        int deletedIndex = getIndex(boardId);
        int deletedId = boardList.remove(deletedIndex).getId();

        return deletedId;
    }

    public void addUserInBoard(int boardId, User joinUser) {
        Board board = getById(boardId);

        board.addUser(joinUser);
    }

    public List<User> getAllUser(int boardId) {
        Board board = boardList.get(getIndex(boardId));

        return board.getUserList();
    }

    private int getIndex(int id) {
        for ( int i = 0; i < boardList.size(); i++ ) {
            if ( boardList.get(i).getId() == id ) {
                return i;
            }
        }

        return -1;
    }
}
