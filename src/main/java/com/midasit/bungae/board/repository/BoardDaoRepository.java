package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class BoardDaoRepository implements RepositoryInterface {
    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Board> boardRowMapper = new RowMapper<Board>() {
        public Board mapRow(ResultSet rs, int i) throws SQLException {
            Board board = new Board();
            board.setNo(rs.getInt("no"));
            board.setTitle(rs.getString("title"));
            board.setUserNo(rs.getInt("user_no"));
            board.setPassword(rs.getString("password"));
            board.setImage(rs.getString("image"));
            board.setContent(rs.getString("content"));
            board.setMaxUserCount(rs.getInt("max_user_count"));

            return board;
        }
    };

    public BoardDaoRepository() { }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Board> getAll() {
        return this.jdbcTemplate.query("select * from board order by no asc",
                                        this.boardRowMapper);
    }

    public Board getByNo(int no) {
        return this.jdbcTemplate.queryForObject("select * from board where no = ?",
                                                new Object[] { no },
                                                this.boardRowMapper);
    }

    public int add(final Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into board(no, title, password, image, content, max_user_count, user_no) values (null, ?, ?, ?, ?, ?, ?)",
                                                            Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, board.getTitle());
                ps.setString(2, board.getPassword());
                ps.setString(3, board.getImage());
                ps.setString(4, board.getContent());
                ps.setInt(5, board.getMaxUserCount());
                ps.setInt(6, board.getUserNo());

                return ps;
            }
        }, keyHolder);

        this.jdbcTemplate.update("insert into board_user_list(no, board_no, user_no) values(null, ?, ?)", keyHolder.getKey().intValue(), board.getUserNo());

        return keyHolder.getKey().intValue();
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from board", Integer.class);
    }

    public void update(int boardNo, String title, String image, String content) {
        this.jdbcTemplate.update("update board set title = ?, image = ?, content = ? where no = ?",
                                 title,
                                 image,
                                 content,
                                 boardNo);
    }

    public void delete(final int boardNo) {
        this.jdbcTemplate.update("delete from board where no = ?",
                                 boardNo);
    }

    public void deleteAll() {
        // board 전체 삭제
        this.jdbcTemplate.update("delete from board");
        // board_user_list 전체 삭제
        this.jdbcTemplate.update("delete from board_user_list");
    }

    public int getUserCount(int boardNo) {
        return this.jdbcTemplate.queryForObject("select count(*) from board_user_list where board_no = ?",
                                                new Object[] { boardNo },
                                                Integer.class);
    }

    public void addUserNoIntoBoard(int boardNo, int joinUserNo) {
        this.jdbcTemplate.update("insert into board_user_list(no, board_no, user_no) values(null, ?, ?)",
                                 boardNo,
                                 joinUserNo);
    }

    public List<Integer> getAllUser(int boardNo) {
        return this.jdbcTemplate.queryForList("select user_no from board_user_list where board_no = ?",
                                              new Object[] { boardNo },
                                              Integer.class);
    }

    public int deleteUserAtBoard(int boardNo, int userNo) {
        int deletedUserNo = this.jdbcTemplate.queryForObject("select user_no from board_user_list where board_no = ? and user_no = ?",
                                                            new Object[] { boardNo, userNo },
                                                            Integer.class);

        this.jdbcTemplate.update("delete from board_user_list where board_no = ? and user_no = ?",
                                 boardNo,
                                 userNo);

        return deletedUserNo;
    }

    public int hasUserNoAtBoard(int boardNo, int userNo) {
        int hasUser = this.jdbcTemplate.queryForObject("select EXISTS (select * from board_user_list where board_no = ? and user_no = ?)",
                                                           new Object[] { boardNo, userNo },
                                                           Integer.class);
        return hasUser;
    }
}
