package com.midasit.bungae.notice.repository;

import com.midasit.bungae.admin.dto.Notice;
import com.midasit.bungae.generator.mapper.NoticeMapper;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
@Transactional
public class NoticeDao implements NoticeRepository {
    @Autowired
    NoticeMapper noticeMapper;

    public NoticeDao() { }

    @Override
    public List<Notice> getAll() {
        return noticeMapper.selectAll();
    }

    @Override
    public Notice get(int no) {
        return noticeMapper.selectByNo(no);
    }

    @Override
    public int add(Notice notice) {
        noticeMapper.insert(notice);

        return notice.getNo();
    }

    @Override
    public void update(Notice notice) {
        noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public void delete(Integer noticeNo) {
        noticeMapper.deleteByPrimaryKey(noticeNo);
    }
}
