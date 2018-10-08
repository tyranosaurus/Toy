package com.midasit.bungae.user.repository;

import com.midasit.bungae.generator.model.UserExample;
import com.midasit.bungae.generator.repository.UserMapper;
import com.midasit.bungae.user.Gender;
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

@Repository
public class UserDao implements UserRepository {
    @Autowired
    UserMapper mapper;

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
        return mapper.selectByPrimaryKey(userNo);
    }

    @Override
    public User get(String id) {
        UserExample example = new UserExample();
        example.createCriteria()
               .andIdEqualTo(id);

        return mapper.selectByExample(example).get(0);
    }

    @Override
    public String getAuthority(String id) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdEqualTo(id);


        String sql = "select authority from user_authority where user_name = ?";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { id },
                                                String.class);
    }

    @Override
    public String getAuthority(int no) {
        String sql = "select authority from user_authority where user_no = ?";

        return this.jdbcTemplate.queryForObject(sql,
                                                new Object[] { no },
                                                String.class);
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

    @Override
    public boolean hasId(String id) {
        String sql = "select exists (select * " +
                                     "from user " +
                                     "where id = ?)";

        if ( this.jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class) > 0 ) {
            return true;
        }

        return false;
    }

    @Override
    public int create(User user) {
        mapper.insert(user);

        return user.getNo();
    }

    @Override
    public int createAuthority(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into user_authority(no, user_name, password, enabled, authority, user_no) " +
                             "values(null, ?, ?, 1, ?, ?);";

                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getId());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getAuthority());
                ps.setInt(4, user.getNo());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
