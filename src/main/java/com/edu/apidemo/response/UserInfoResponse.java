package com.edu.apidemo.response;

import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String fullname;
    private String password;
    private int yearOfBirth;
    private List<String> roles;

    public UserInfoResponse() {
    }

    public UserInfoResponse(Long id, String username, String fullname, int yearOfBirth, List<String> roles) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.yearOfBirth = yearOfBirth;
        this.roles = roles;
    }
//    public UserInfoResponse(Long id, String username, String fullname, String password, int yearOfBirth, List<String> roles) {
//        this.id = id;
//        this.username = username;
//        this.fullname = fullname;
//        this.password = password;
//        this.yearOfBirth = yearOfBirth;
//        this.roles = roles;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", roles=" + roles +
                '}';
    }
}
