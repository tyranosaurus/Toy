package com.midasit.bungae.board.dto;

import com.midasit.bungae.board.Gender;

public class User {
    private int no;
    private String id;
    private String password;
    private String name;
    private String email;
    private Gender gender;

    public User() { }

    public User(int no, String id, String password, String name, String email, Gender gender) {
        this.no = no;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
