package com.midasit.bungae.notice.repository;

import com.midasit.bungae.admin.dto.Notice;

import java.util.List;

public interface NoticeRepository {
    List<Notice> getAll();
    Notice get(int no);
    int add(Notice notice);
    void update(Notice notice);
    void delete(Integer noticeNo);
}
