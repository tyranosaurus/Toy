package com.midasit.bungae.generator.mapper;

import com.midasit.bungae.generator.model.UserAuthority;
import com.midasit.bungae.generator.model.UserAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAuthorityMapper {
    long countByExample(UserAuthorityExample example);

    int deleteByExample(UserAuthorityExample example);

    int deleteByPrimaryKey(Integer no);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    List<UserAuthority> selectByExample(UserAuthorityExample example);

    UserAuthority selectByPrimaryKey(Integer no);

    int updateByExampleSelective(@Param("record") UserAuthority record, @Param("example") UserAuthorityExample example);

    int updateByExample(@Param("record") UserAuthority record, @Param("example") UserAuthorityExample example);

    int updateByPrimaryKeySelective(UserAuthority record);

    int updateByPrimaryKey(UserAuthority record);
}