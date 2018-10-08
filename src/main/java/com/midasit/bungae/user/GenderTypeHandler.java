package com.midasit.bungae.user;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderTypeHandler implements TypeHandler<Gender> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Gender gender, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, gender.getValue());
    }

    @Override
    public Gender getResult(ResultSet rs, String column) throws SQLException {
        return Gender.valueOf(rs.getInt(column));
    }

    @Override
    public Gender getResult(ResultSet rs, int index) throws SQLException {
        return Gender.valueOf(rs.getInt(index));
    }

    @Override
    public Gender getResult(CallableStatement cs, int index) throws SQLException {
        return Gender.valueOf(cs.getInt(index));
    }
}
