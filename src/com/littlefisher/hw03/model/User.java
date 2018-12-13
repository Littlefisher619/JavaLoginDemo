package com.littlefisher.hw03.model;


public class User {
    private Integer id=0;
    private String username;
    private String password;
    private String name="";
    private Integer gender=-1;
    private String phone="";
    public User() {

    }
    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }
    public User(Integer id, String username, String password, String name, Integer gender, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }
    public User(String username, String password, String name, Integer gender, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public User clone(){
        return new User(this.id,this.username,this.password,this.name,this.gender,this.phone);
    }

}
