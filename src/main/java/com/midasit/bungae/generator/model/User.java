package com.midasit.bungae.generator.model;

public class User {
    private Integer no;

    private String id;

    private String password;

    private String name;

    private String email;

    private byte[] gender;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public byte[] getGender() {
        return gender;
    }

    public void setGender(byte[] gender) {
        this.gender = gender;
    }
}