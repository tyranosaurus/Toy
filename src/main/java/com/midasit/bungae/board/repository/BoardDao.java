package com.midasit.bungae.board.repository;

import com.midasit.bungae.admin.board.dto.Notice;
import com.midasit.bungae.user.Gender;
import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class BoardDao implements BoardRepository {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Board> boardRowMapper = new RowMapper<Board>() {
        public Board mapRow(ResultSet rs, int i) throws SQLException {
            Board board = new Board(rs.getInt("boardNo"),
                                    rs.getString("boardTitle"),
                                    rs.getString("boardPassword"),
                                    rs.getString("boardImage"),
                                    rs.getString("boardContent"),
                                    rs.getInt("boardMaxUserCount"),
                                    new User(rs.getInt("userNo"),
                                             rs.getString("userId"),
                                             rs.getString("userPassword"),
                                             rs.getString("userName"),
                                             rs.getString("userEmail"),
                                             Gender.valueOf(rs.getInt("userGender"))));

            return board;
        }
    };

    public BoardDao() { }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Board get(int no) {
        String sql = "select b.no as boardNo, " +
                             "b.title as boardTitle, " +
                             "b.password as boardPassword, " +
                             "b.image as boardImage, " +
                             "b.content as boardContent, " +
                             "b.max_user_count as boardMaxUserCount, " +
                             "u.no as userNo, " +
                             "u.id as userId, " +
                             "u.password as userPassword, " +
                             "u.name as userName, " +
                             "u.email as userEmail, " +
                             "u.gender as userGender " +
                     "from board as b, " +
                           "user as u " +
                     "where b.user_no = u.no " +
                            "and b.no = ?";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { no },
                                                this.boardRowMapper);
    }

    @Override
    public List<Board> getAll() {
        String sql = "select b.no as boardNo, " +
                             "b.title as boardTitle, " +
                             "b.password as boardPassword, " +
                             "b.image as boardImage, " +
                             "b.content as boardContent, " +
                             "b.max_user_count as boardMaxUserCount, " +
                             "u.no as userNo, " +
                             "u.id as userId, " +
                             "u.password as userPassword, " +
                             "u.name as userName, " +
                             "u.email as userEmail, " +
                             "u.gender as userGender " +
                     "from board as b, " +
                           "user as u " +
                     "where b.user_no = u.no";

        return this.jdbcTemplate.query(sql,
                                       this.boardRowMapper);
    }

    @Override
    public int add(final Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into board(no, title, password, image, content, max_user_count, user_no) " +
                             "values (null, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, board.getTitle());
                ps.setString(2, board.getPassword());
                ps.setString(3, board.getImage());
                ps.setString(4, board.getContent());
                ps.setInt(5, board.getMaxParticipantCount());
                ps.setInt(6, board.getWriter().getNo());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public int getCount() {
        String sql = "select count(*) " +
                     "from board";

        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void update(int boardNo, String title, String image, String content, int maxParticipantCount, String password) {
        String sql = "update board " +
                "set title = ?, " +
                "image = ?, " +
                "content = ?, " +
                "max_user_count = ? " +
                "where no = ? " +
                "and password = ?";

        this.jdbcTemplate.update(sql,
                title,
                image,
                content,
                maxParticipantCount,
                boardNo,
                password);
    }

    @Override
    public void update(int boardNo, String title, String image, String content, int maxParticipantCount) {
        String sql = "update board " +
                     "set title = ?, " +
                         "image = ?, " +
                         "content = ?, " +
                         "max_user_count = ? " +
                     "where no = ? ";

        this.jdbcTemplate.update(sql,
                                 title,
                                 image,
                                 content,
                                 maxParticipantCount,
                                 boardNo);
    }

    @Override
    public void delete(int boardNo) {
        String sql = "delete " +
                     "from board " +
                     "where no = ?";

        this.jdbcTemplate.update(sql,
                                 boardNo);
    }

    @Override
    public void deleteAll() {
        String sql = "delete " +
                     "from board";

        this.jdbcTemplate.update(sql);
    }
}
