package com.midasit.bungae.boarduser.repository;

import com.midasit.bungae.generator.mapper.BoardUserMapper;
import com.midasit.bungae.generator.model.BoardUser;
import com.midasit.bungae.generator.model.BoardUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class BoardUserDao implements BoardUserRepository {
    @Autowired
    BoardUserMapper boardUserMapper;

    public BoardUserDao() { }

    @Override
    public int add(final int boardNo, final int participantNo) {
        BoardUser boardUser = new BoardUser(0, boardNo, participantNo);
        boardUserMapper.insert(boardUser);

        return boardUser.getNo();
    }

    @Override
    public void deleteBoard(int boardNo) {
        BoardUserExample example = new BoardUserExample();
        example.createCriteria()
                .andBoardNoEqualTo(boardNo);

        boardUserMapper.deleteByExample(example);
    }

    @Override
    public void deleteParticipant(int boardNo, int participantNo) {
        BoardUserExample example = new BoardUserExample();
        example.createCriteria()
                .andBoardNoEqualTo(boardNo)
                .andUserNoEqualTo(participantNo);

        boardUserMapper.deleteByExample(example);
    }

    @Override
    public void deleteAll() {
        BoardUserExample example = new BoardUserExample();
        example.createCriteria().andNoGreaterThan(0);

        boardUserMapper.deleteByExample(example);
    }

    @Override
    public int getParticipantCount(int boardNo) {
        BoardUserExample example = new BoardUserExample();
        example.createCriteria()
                .andBoardNoEqualTo(boardNo);

        return boardUserMapper.countByExample(example);

    }

    @Override
    public void addParticipant(int boardNo, int participantNo) {
        boardUserMapper.insert(new BoardUser(0, boardNo, participantNo));
    }

    @Override
    public List<Integer> getParticipantNoList(int boardNo) {
        return boardUserMapper.selectAllParticipant(boardNo);
    }

    @Override
    public int hasParticipant(int boardNo, int participantNo) {
        return boardUserMapper.selectExistParticipant(new BoardUser(0, boardNo, participantNo));
    }
}
