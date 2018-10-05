package com.midasit.bungae.board.dto;

import com.midasit.bungae.user.dto.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Board {
    private int no;
    @NotNull
    @Size(min = 1)
    private String title;
    private String password;
    private String image;
    @NotNull
    @Size(min = 1)
    private String content;
    @NotNull
    @Min(1)
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

    @Override
    public String toString() {
        return "Board{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", maxParticipantCount=" + maxParticipantCount +
                ", writer=" + writer +
                '}';
    }
}
