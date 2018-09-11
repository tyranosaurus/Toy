package com.midasit.bungae.board.dto;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int no;
    private String title;
    private String password;
    private String image;
    private String content;
    private int maxUserCount;
    private int userNo;
    private List<Integer> userNoList = new ArrayList();

    public Board() { }

    public Board(int no, String title, int userNo, String password, String image, String content, int maxUserCount) {
        this.no = no;
        this.title = title;
        this.userNo = userNo;
        this.password = password;
        this.image = image;
        this.content = content;
        this.maxUserCount = maxUserCount;

        this.userNoList.add(userNo);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(int maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public List<Integer> getUserNoList() {
        return userNoList;
    }

    public void setUserNoList(List<Integer> userNoList) {
        this.userNoList = userNoList;
    }

    public void addUser(int joinUserNo) {
        userNoList.add(joinUserNo);
    }
}
