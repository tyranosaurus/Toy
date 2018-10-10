package com.midasit.bungae.generator.mapper;

import com.midasit.bungae.generator.model.BoardUser;
import com.midasit.bungae.generator.model.BoardUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardUserMapper {
    int countByExample(BoardUserExample example);

    int deleteByExample(BoardUserExample example);

    int deleteByPrimaryKey(Integer no);

    int insert(BoardUser record);

    int insertSelective(BoardUser record);

    List<BoardUser> selectByExample(BoardUserExample example);

    BoardUser selectByPrimaryKey(Integer no);

    int updateByExampleSelective(@Param("record") BoardUser record, @Param("example") BoardUserExample example);

    int updateByExample(@Param("record") BoardUser record, @Param("example") BoardUserExample example);

    int updateByPrimaryKeySelective(BoardUser record);

    int updateByPrimaryKey(BoardUser record);

    int selectExistParticipant(BoardUser record);

    List<Integer> selectAllParticipant(int boardNo);
}