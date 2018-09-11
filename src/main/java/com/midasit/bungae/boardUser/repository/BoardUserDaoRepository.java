package com.midasit.bungae.boardUser.repository;

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
public class BoardUserDaoRepository implements BoardUserRepositoryInterface {
    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public BoardUserDaoRepository() { }

    public int add(final int boardNo, final int userNo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into board_user(no, board_no, user_no) values(null, ?, ?)",
                                                            Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, boardNo);
                ps.setInt(2, userNo);

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void delete(int boardNo) {
        this.jdbcTemplate.update("delete from board_user where board_no = ?",
                                 boardNo);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from board_user");
    }

    public int getUserCountAtBoard(int boardNo) {
        return this.jdbcTemplate.queryForObject("select count(*) from board_user where board_no = ?",
                                                new Object[] { boardNo },
                                                Integer.class);
    }

    public void addUserNoToBoard(int boardNo, int joinUserNo) {
        this.jdbcTemplate.update("insert into board_user(no, board_no, user_no) values(null, ?, ?)",
                                 boardNo,
                                 joinUserNo);
    }

    public List<Integer> getAllUserAtBoard(int boardNo) {
        return this.jdbcTemplate.queryForList("select user_no from board_user where board_no = ?",
                                              new Object[] { boardNo },
                                              Integer.class);
    }

    public void deleteUserAtBoard(int boardNo, int userNo) {
        this.jdbcTemplate.update("delete from board_user where board_no = ? and user_no = ?",
                                 boardNo,
                                 userNo);
    }

    public int hasUserNoAtBoard(int boardNo, int userNo) {
        return this.jdbcTemplate.queryForObject("select EXISTS (select * from board_user where board_no = ? and user_no = ?)",
                                                new Object[] { boardNo, userNo },
                                                Integer.class);
    }
}
