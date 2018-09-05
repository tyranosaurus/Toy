package com.midasit.bungae.board.dto;

public class Board {
    private int id;
    private String title;
    private String writer;
    private String password;
    private String image;
    private String content;

    public Board() { }

    public Board(int id, String title, String writer, String password, String image, String content) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.password = password;
        this.image = image;
        this.content = content;
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
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
}
