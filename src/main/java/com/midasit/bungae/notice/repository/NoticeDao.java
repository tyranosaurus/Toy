package com.midasit.bungae.notice.repository;

import com.midasit.bungae.admin.board.dto.Notice;
import com.midasit.bungae.board.dto.Board;
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
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Notice> noticeRowMapper = new RowMapper<Notice>() {
        public Notice mapRow(ResultSet rs, int i) throws SQLException {
            Notice notice = new Notice(rs.getInt("noticeNo"),
                    rs.getString("noticeTitle"),
                    rs.getString("noticePassword"),
                    rs.getString("noticeImage"),
                    rs.getString("noticeContent"),
                    new User(rs.getInt("userNo"),
                            rs.getString("userId"),
                            rs.getString("userPassword"),
                            rs.getString("userName"),
                            rs.getString("userEmail"),
                            Gender.valueOf(rs.getInt("userGender"))));

            return notice;
        }
    };

    public NoticeDao() { }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Notice> getAll() {
        String sql = "select n.no as noticeNo, " +
                             "n.title as noticeTitle, " +
                             "n.password as noticePassword, " +
                             "n.image as noticeImage, " +
                             "n.content as noticeContent, " +
                             "u.no as userNo, " +
                             "u.id as userId, " +
                             "u.password as userPassword, " +
                             "u.name as userName, " +
                             "u.email as userEmail, " +
                             "u.gender as userGender " +
                     "from notice as n, " +
                           "user as u " +
                     "where n.user_no = u.no ";

        return this.jdbcTemplate.query(sql,
                                       this.noticeRowMapper);
    }

    @Override
    public Notice get(int no) {
        String sql = "select n.no as noticeNo, " +
                             "n.title as noticeTitle, " +
                             "n.password as noticePassword, " +
                             "n.image as noticeImage, " +
                             "n.content as noticeContent, " +
                             "u.no as userNo, " +
                             "u.id as userId, " +
                             "u.password as userPassword, " +
                             "u.name as userName, " +
                             "u.email as userEmail, " +
                             "u.gender as userGender " +
                     "from notice as n, " +
                           "user as u " +
                     "where n.user_no = u.no and n.no = ?";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { no },
                                                this.noticeRowMapper);
    }

    @Override
    public int add(Notice notice) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into notice(no, title, password, image, content, user_no) " +
                        "values (null, ?, ?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, notice.getTitle());
                ps.setString(2, notice.getPassword());
                ps.setString(3, notice.getImage());
                ps.setString(4, notice.getContent());
                ps.setInt(5, notice.getWriter().getNo());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(int noticeNo, String title, String image, String content, String password) {
        String sql = "update notice " +
                     "set title = ?, " +
                         "image = ?, " +
                         "content = ? " +
                     "where no = ? " +
                            "and password = ?";

        this.jdbcTemplate.update(sql,
                                 title,
                                 image,
                                 content,
                                 noticeNo,
                                 password);
    }

    @Override
    public void delete(Integer noticeNo) {
        String sql = "delete " +
                     "from notice " +
                     "where no = ?";

        this.jdbcTemplate.update(sql,
                                 noticeNo);
    }
}
