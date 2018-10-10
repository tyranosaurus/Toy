package com.midasit.bungae.generator.model;

public class BoardUser {
    private Integer no;

    private Integer boardNo;

    private Integer userNo;

    public BoardUser() { }

    public BoardUser(Integer no, Integer boardNo, Integer userNo) {
        this.no = no;
        this.boardNo = boardNo;
        this.userNo = userNo;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(Integer boardNo) {
        this.boardNo = boardNo;
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }
}