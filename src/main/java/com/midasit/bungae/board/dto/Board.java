package com.midasit.bungae.board.dto;

import com.midasit.bungae.board.dao.User;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int id;
    private String title;
    private User writer;
    private String password;
    private String image;
    private String content;
    private int maxUserCount;
    private List<User> userList = new ArrayList<User>();

    public Board() { }

    public Board(int id, String title, User writer, String password, String image, String content, int maxUserCount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.password = password;
        this.image = image;
        this.content = content;
        this.maxUserCount = maxUserCount;

        this.userList.add(writer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User joinUser) {
        userList.add(joinUser);
    }
}
