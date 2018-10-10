package com.midasit.bungae.generator.mapper;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.generator.model.BoardExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    int countByExample(BoardExample example);

    int deleteByExample(BoardExample example);

    int deleteByPrimaryKey(Integer no);

    int insert(Board record);

    int insertSelective(Board record);

    List<Board> selectByExample(BoardExample example);

    Board selectByPrimaryKey(Integer no);

    int updateByExampleSelective(@Param("record") Board record, @Param("example") BoardExample example);

    int updateByExample(@Param("record") Board record, @Param("example") BoardExample example);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);

    Board selectByNo(Integer no);

    List<Board> selectAll();
}