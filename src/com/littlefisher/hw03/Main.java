package com.littlefisher.hw03;

import com.littlefisher.hw03.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<User> users;
    public static void main(String[] args) {
        UserDao userDao=new UserDao();

        try {
            users=userDao.queryALL();

        } catch (SQLException e) {
            System.out.println("DB Error");
            e.printStackTrace();
            System.exit(0);
        }
        ConsoleInteraction interaction=new ConsoleInteraction();
        interaction.start();

    }
}
