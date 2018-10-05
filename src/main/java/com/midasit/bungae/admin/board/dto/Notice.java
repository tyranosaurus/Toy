package com.midasit.bungae.admin.board.dto;

import com.midasit.bungae.user.dto.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Notice {
    private int no;
    @NotNull
    @Size(min = 1)
    private String title;
    private String password;
    private String image;
    @NotNull
    @Size(min = 1)
    private String content;
    private User writer;

    public Notice() { }

    public Notice(int no, String title, String password, String image, String content, User writer) {
        this.no = no;
        this.title = title;
        this.password = password;
        this.image = image;
        this.content = content;
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", writer=" + writer.toString() +
                '}';
    }
}
