package com.littlefisher.hw03;

import com.littlefisher.hw03.model.User;
import com.littlefisher.hw03.util.CryptoMD5;
import com.littlefisher.hw03.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void addUser(User u) throws SQLException {
        //获取连接
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "INSERT INTO user(username, password, name, gender, phone)"
                +"values("+"?,?,?,?,?)";
        //预编译
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        ptmt.setString(1, u.getUsername());
        ptmt.setString(2, u.getPassword());
        ptmt.setString(3, u.getName());
        ptmt.setInt(4, u.getGender());
        ptmt.setString(5, u.getPhone());

        //执行
        ptmt.execute();
    }
    public void updateUser(User u) throws SQLException {
        //获取连接
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "UPDATE user set username=?,password=?,name=?,gender=?,phone=? WHERE id=?";
        //预编译
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        ptmt.setString(1, u.getUsername());
        ptmt.setString(2, u.getPassword());
        ptmt.setString(3, u.getName());
        ptmt.setInt(4, u.getGender());
        ptmt.setString(5, u.getPhone());
        ptmt.setInt(6, u.getId());
        //执行
        ptmt.execute();
    }
    public List<User> queryALL() throws SQLException {
        Connection conn = DbUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM user");

        List<User> users = new ArrayList<User>();
        User u = null;
        while(rs.next()){
            u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setName(rs.getString("name"));
            u.setGender(rs.getInt("gender"));
            u.setPhone(rs.getString("phone"));

            users.add(u);
        }
        return users;
    }
    public User login(String username,String password) throws SQLException {
        Connection conn = DbUtil.getConnection();
        String sql="SELECT * FROM user WHERE username = '"+username+"' AND password = '"+ password+"'";
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        //ptmt.setString(1, username);
        //ptmt.setString(2, CryptoMD5.md5(password));

        //执行

        ResultSet rs = ptmt.executeQuery(sql);
        User u = null;
        if(rs.next()){
            u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setName(rs.getString("name"));
            u.setGender(rs.getInt("gender"));
            u.setPhone(rs.getString("phone"));
        }
        return u;
    }
    public User find(String usrname) throws SQLException {
        Connection conn = DbUtil.getConnection();
        String sql="SELECT * FROM user WHERE username='"+usrname+"'";
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        //ptmt.setString(1, usrname);
        //执行

        ResultSet rs = ptmt.executeQuery(sql);
        User u = null;
        if(rs.next()){
            u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setName(rs.getString("name"));
            u.setGender(rs.getInt("gender"));
            u.setPhone(rs.getString("phone"));
        }
        return u;
    }


}
