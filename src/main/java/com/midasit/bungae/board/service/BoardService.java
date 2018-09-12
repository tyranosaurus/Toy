package com.midasit.bungae.board.service;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.boarddetail.dto.BoardDetail;
import com.midasit.bungae.user.dto.User;

import java.util.List;

public interface BoardService {
    List<Board> getAll();
    Board get(int no);
    int createNew(Board board);
    int getCount();
    boolean isEqualWriter(int boardNo, User loginUser);
    boolean isEqualPassword(int boardNo, String rightPassword);
    void modify(int boardNo, String title, String image, String content);
    void delete(int boardNo);
    void deleteAll();
    int getParticipantCount(int boardNo);
    List<User> getParticipants(int boardNo);
    void participate(int boardNo, int participantNo);
    void cancelParticipation(int boardNo, int userNo);
    BoardDetail getDetail(int boardNo);
}
