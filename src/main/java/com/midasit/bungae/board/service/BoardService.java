package com.midasit.bungae.board.service;

import com.midasit.bungae.admin.dto.Notice;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.user.dto.User;

import java.util.List;

public interface BoardService {
    List<Notice> getNotices();
    Notice getNotice(int noticeNo);
    List<Board> getAll();
    Board get(int no);
    int createNew(Board board);
    int createNotice(Notice notice);
    int getCount();
    boolean isEqualWriter(int boardNo, int writerNo);
    boolean isEqualPassword(int boardNo, String password);
    void modify(int boardNo, String title, String image, String content, int maxParticipantCount, String password, int writerNo);
    void modifyNotice(int noticeNo, String title, String image, String content, String password, int writerNo);
    void delete(int boardNo, String password, int writerNo);
    void deleteAll();
    void deleteNotice(Integer noticeNo, String password, int no);
    void deleteByAdmin(Integer boardNo, String authority);
    int getParticipantCount(int boardNo);
    List<User> getParticipants(int boardNo);
    void participate(int boardNo, int participantNo);
    void cancelParticipation(int boardNo, int userNo);
}
