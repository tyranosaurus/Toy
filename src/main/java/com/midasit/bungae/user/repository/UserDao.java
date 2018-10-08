package com.midasit.bungae.user.repository;

import com.midasit.bungae.generator.mapper.UserAuthorityMapper;
import com.midasit.bungae.generator.mapper.UserMapper;
import com.midasit.bungae.generator.model.UserAuthority;
import com.midasit.bungae.generator.model.UserAuthorityExample;
import com.midasit.bungae.generator.model.UserExample;
import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements UserRepository {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserAuthorityMapper authorityMapper;

    public UserDao() { }

    @Override
    public User get(int userNo) {
        return userMapper.selectByPrimaryKey(userNo);
    }

    @Override
    public User get(String id) {
        UserExample example = new UserExample();
        example.createCriteria()
               .andIdEqualTo(id);

        return userMapper.selectByExample(example).get(0);
    }

    @Override
    public String getAuthority(String id) {
        UserAuthorityExample example = new UserAuthorityExample();
        example.createCriteria()
               .andUserNameEqualTo(id);

        return authorityMapper.selectByExample(example).get(0).getAuthority();
    }

    @Override
    public String getAuthority(int no) {
        UserAuthorityExample example = new UserAuthorityExample();
        example.createCriteria()
                .andUserNoEqualTo(no);

        return authorityMapper.selectByExample(example).get(0).getAuthority();
    }

    @Override
    public int hasUser(String id, String password) {

        return userMapper.selectExist(new User(0, id, password, null, null, null));
    }

    @Override
    public boolean hasId(String id) {
        if ( userMapper.selectExistId(id) > 0 ) {
            return true;
        }

        return false;
    }

    @Override
    public int create(User user) {
        userMapper.insert(user);

        return user.getNo();
    }

    @Override
    public int createAuthority(UserAuthority userAuthority) {
        authorityMapper.insert(userAuthority);

        return userAuthority.getNo();
    }
}
