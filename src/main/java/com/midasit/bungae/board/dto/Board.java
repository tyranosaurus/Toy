package com.midasit.bungae.board.dto;

import com.midasit.bungae.user.dto.User;

public class Board {
    private int no;
    private String title;
    private String password;
    private String image;
    private String content;
    private int maxParticipantCount;
    private User writer;

    public Board() { }

    public Board(int no, String title, String password, String image, String content, int maxParticipantCount, User writer) {
        this.no = no;
        this.title = title;
        this.password = password;
        this.image = image;
        this.content = content;
        this.maxParticipantCount = maxParticipantCount;
        this.writer = writer;
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

    public int getMaxParticipantCount() {
        return maxParticipantCount;
    }

    public void setMaxParticipantCount(int maxParticipantCount) {
        this.maxParticipantCount = maxParticipantCount;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}
