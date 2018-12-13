package com.littlefisher.hw03.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbUtil {
    public static final String URL = "jdbc:mysql://localhost:3306/w2ol?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF8";
    public static final String USER = "w2ol";
    public static final String PASSWORD = "w2ol";
    private static Connection conn = null;
    static{
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 获得数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conn;
    }
}
