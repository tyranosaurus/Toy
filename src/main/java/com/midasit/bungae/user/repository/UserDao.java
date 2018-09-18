package com.midasit.bungae.user.repository;

import com.midasit.bungae.user.Gender;
import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User(rs.getInt("no"),
                                 rs.getString("id"),
                                 rs.getString("password"),
                                 rs.getString("name"),
                                 rs.getString("email"),
                                 Gender.valueOf(rs.getInt("gender")));

            return user;
        }
    };

    public UserDao() { }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User get(int userNo) {
        String sql = "select * " +
                     "from user " +
                     "where no = ?";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { userNo },
                                                this.userRowMapper);
    }

    @Override
    public int hasUser(String id, String password) {
        String sql = "select exists (select * " +
                                     "from user " +
                                     "where id = ? " +
                                            "and password = ?)";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { id, password },
                                                Integer.class);
    }
}
