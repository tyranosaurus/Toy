package com.midasit.bungae.board.repository;

import com.midasit.bungae.board.dto.Board;
import com.midasit.bungae.generator.mapper.BoardMapper;
import com.midasit.bungae.generator.model.BoardExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao implements BoardRepository {
    @Autowired
    BoardMapper boardMapper;

    public BoardDao() { }

    @Override
    public Board get(int no) {
        return boardMapper.selectByNo(no);
    }

    @Override
    public List<Board> getAll() {
        return boardMapper.selectAll();
    }

    @Override
    public int add(Board board) {
        boardMapper.insert(board);

        return board.getNo();
    }

    @Override
    public int getCount() {
        BoardExample example = new BoardExample();
        example.createCriteria()
                .andNoGreaterThan(0);

        return boardMapper.countByExample(example);
    }

    @Override
    public void update(Board board) {
        boardMapper.updateByPrimaryKeySelective(board);
    }

    @Override
    public void delete(int boardNo) {
        boardMapper.deleteByPrimaryKey(boardNo);
    }

    @Override
    public void deleteAll() {
        BoardExample example = new BoardExample();
        example.createCriteria()
                .andNoGreaterThan(0);

        boardMapper.deleteByExample(example);
    }
}
