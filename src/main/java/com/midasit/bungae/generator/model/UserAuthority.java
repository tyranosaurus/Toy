package com.midasit.bungae.generator.model;

public class UserAuthority {
    private Integer no;

    private String userName;

    private String password;

    private int enabled;

    private String authority;

    private Integer userNo;

    public UserAuthority() { }

    public UserAuthority(Integer no, String userName, String password, int enabled, String authority, Integer userNo) {
        this.no = no;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
        this.userNo = userNo;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority == null ? null : authority.trim();
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }
}