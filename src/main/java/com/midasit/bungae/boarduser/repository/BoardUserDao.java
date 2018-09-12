package com.midasit.bungae.boarduser.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class BoardUserDao implements BoardUserRepository {
    private JdbcTemplate jdbcTemplate;

    public BoardUserDao() { }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int add(final int boardNo, final int participantNo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql ="insert into board_user(no, board_no, user_no) " +
                            "values(null, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, boardNo);
                ps.setInt(2, participantNo);

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteBoard(int boardNo) {
        String sql = "delete " +
                     "from board_user " +
                     "where board_no = ?";

        this.jdbcTemplate.update(sql,
                                 boardNo);
    }

    @Override
    public void deleteParticipant(int boardNo, int participantNo) {
        String sql = "delete " +
                     "from board_user " +
                     "where board_no = ? " +
                            "and user_no = ?";

        this.jdbcTemplate.update(sql,
                                 boardNo,
                                 participantNo);
    }

    @Override
    public void deleteAll() {
        String sql = "delete " +
                     "from board_user";

        this.jdbcTemplate.update(sql);
    }

    @Override
    public int getParticipantCount(int boardNo) {
        String sql = "select count(*) " +
                     "from board_user " +
                     "where board_no = ?";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { boardNo },
                                                Integer.class);
    }

    @Override
    public void addParticipant(int boardNo, int participantNo) {
        String sql = "insert into board_user(no, board_no, user_no) " +
                     "values(null, ?, ?)";

        this.jdbcTemplate.update(sql,
                                 boardNo,
                                 participantNo);
    }

    @Override
    public List<Integer> getParticipantNoList(int boardNo) {
        String sql = "select user_no " +
                     "from board_user " +
                     "where board_no = ?";

        return this.jdbcTemplate.queryForList(sql,
                                              new Object[] { boardNo },
                                              Integer.class);
    }

    @Override
    public int hasParticipant(int boardNo, int participantNo) {
        String sql = "select EXISTS (select * " +
                                     "from board_user " +
                                     "where board_no = ? " +
                                            "and user_no = ?)";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { boardNo, participantNo },
                                                Integer.class);
    }
}
