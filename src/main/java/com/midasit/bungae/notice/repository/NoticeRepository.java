package com.midasit.bungae.notice.repository;

import com.midasit.bungae.admin.board.dto.Notice;

import java.util.List;

public interface NoticeRepository {
    List<Notice> getAll();
    Notice get(int no);
    int add(Notice notice);
    void update(int noticeNo, String title, String image, String content, String password);
    void delete(Integer noticeNo);
}
