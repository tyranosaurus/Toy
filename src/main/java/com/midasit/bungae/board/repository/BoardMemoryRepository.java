package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.board.dto.User;

import java.util.ArrayList;
import java.util.List;

public class BoardMemoryRepository  {
    List<Board> boardList = null;

    public BoardMemoryRepository() {
        boardList = new ArrayList<Board>();
    }

    public List<Board> getAll() {
        return boardList;
    }

    public Board getByNo(int id) {
        return boardList.get(getIndex(id));
    }

    public int add(Board board) {
        boardList.add(board);

        return board.getNo();
    }

    public int getCount() {
        return boardList.size();
    }

    public void update(int boardNo, String title, String image, String content) {
        Board modifiedBoard = boardList.get(getIndex(boardNo));

        modifiedBoard.setTitle(title);
        modifiedBoard.setImage(image);
        modifiedBoard.setContent(content);
    }

    public void delete(int boardNo) {
        int deletedIndex = getIndex(boardNo);
        int deletedId = boardList.remove(deletedIndex).getNo();

        //return deletedId;
    }

    public void deleteAll() {

    }

    public void addUserIntoBoard(int boardNo, int joinUser) {
        Board board = getByNo(boardNo);

        board.addUser(joinUser);
    }

    public List<Integer> getAllUser(int boardNo) {
        Board board = boardList.get(getIndex(boardNo));

        return board.getUserNoList();
    }

    public void addUserNoIntoBoard(int boardNo, int joinUserNo) {

    }

    public int getUserCount(int boardNo) {
        return 0;
    }

    public int deleteUserAtBoard(int boardNo, int userNo) {
        return 0;
    }

    public int hasUserNoAtBoard(int boardNo, int userNo) {
        return 0;
    }

    private int getIndex(int id) {
        for ( int i = 0; i < boardList.size(); i++ ) {
            if ( boardList.get(i).getNo() == id ) {
                return i;
            }
        }

        return -1;
    }
}
