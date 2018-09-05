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
        int i = getIndex(id);

        return boardList.get(i);
    }

    public void add(Board board) {
        boardList.add(board);
    }

    public int getCount() {
        return boardList.size();
    }

    public void update(Board modifiedBoard) {
        int modifiedIndex = getIndex(modifiedBoard.getId());

        boardList.get(modifiedIndex).setTitle(modifiedBoard.getTitle());
        boardList.get(modifiedIndex).setWriter(modifiedBoard.getWriter());
        boardList.get(modifiedIndex).setImage(modifiedBoard.getImage());
        boardList.get(modifiedIndex).setContent(modifiedBoard.getContent());
    }

    public int delete(Board deletedBoard) {
        int deletedIndex = getIndex(deletedBoard.getId());
        int deletedId = boardList.remove(deletedIndex).getId();

        return deletedId;
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
