package com.littlefisher.hw03;


import com.littlefisher.hw03.model.User;
import com.littlefisher.hw03.util.CryptoMD5;
import com.littlefisher.hw03.util.Filter;

import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import static java.lang.System.console;

public class ConsoleInteraction {
    private static PrintWriter out=new PrintWriter(new BufferedOutputStream(System.out),true);
    private static Scanner s=new Scanner(System.in);
    private User currentUser;
    private UserDao userDao=new UserDao();
    ConsoleInteraction(){

    }

    public void start(){
        while(true){
            while((currentUser=login())==null);
            launchUserMenu();

        }
    }
    private void firstLoginInterface(){
        while (true) {

            out.printf(
                    "Hello, %s.\n" +
                            "Please fill your account information.\n" +
                            "<Gender> <Phone> <Name>\n" +
                            "Gender should be 0(female),1(male) or 2(neutral).\n" +
                            "Phone can only be numbers\n" +
                            "English name should use '¡¤' instead of space and Chinese name cannot include space or '.'\n" +
                            "\n>>>Gender cannot be modified later.\n"
                    , currentUser.getUsername());
            String buf[]=getNextLineNotEmpty().split(" ");
            if(buf.length!=3){
                out.println("Invalid input!");
                anykeytocontinue();
            }
            String genderbuf = buf[0], phone = buf[1], name = buf[2];
            Integer gender = -1;
            if (Filter.legalGender(genderbuf)) {
                gender = Integer.parseInt(genderbuf);
            } else {
                out.println("Invalid gender! Gender should be 0(female),1(male) or 2(neutral).");
                continue;
            }


            if (!Filter.legalPhone(phone)) {
                out.println("Invalid phone! Phone can only be numbers");
                continue;
            }

            if (!Filter.legalName(name)) {
                out.println("Invalid name! English name should use '¡¤' instead of space and Chinese name cannot include space or '.'\n");
                continue;
            }
            User user=currentUser.clone();
            user.setGender(gender);
            user.setPhone(phone);
            user.setName(name);
            try {
                userDao.updateUser(user);
                out.println("Success! Information updated!");
                currentUser=user;
                break;
            } catch (SQLException e) {
                out.println("Sorry, we can't update your information due to a database error!");
                e.printStackTrace();
            }
        }
    }
    private void anykeytocontinue(){
        out.println("[!]Press enter to continue, if doesn't work, one more please.");
        s.nextLine();
        s.nextLine();
    }
    private String getNextLineNotEmpty(){
        while (true) {
            String str = s.nextLine().trim();
            if (!str.isEmpty()) return str;
        }

    }
    private void checkInformation(){
        boolean backtomenu=false;
        while (true) {
            if(backtomenu) break;
            out.printf(
                            "Hey %s, here are your information:\n"+
                            "================================================\n" +
                            "(0)Username: %s\n" +
                            "(1)Password(Hashed): %s\n"+
                            "(2)Name: %s\n"+
                            "(3)Gender: %s\n"+
                            "(4)Phone: %s\n"+
                            "================================================\n\n"+
                            "You can only modify 1,2 and 4.\n"+
                            "Enter what item you want to edit or back to menu with any other numeric inputs.\n"
                    , currentUser.getUsername(),currentUser.getUsername(),currentUser.getPassword(),currentUser.getName(),
                    currentUser.getGender()==0?"\u2640":(currentUser.getGender()==1?"\u2642":(currentUser.getGender()==-1?"unknown":"neutral"))
                    ,currentUser.getPhone());
            if(s.hasNextInt()){
                int option=s.nextInt();
                boolean flagmodify=false;
                User user=currentUser.clone();
                switch (option){
                    case 0:
                        out.println("Sorry, you can't modify your username :)");
                        break;
                    case 1:
                        out.println("Enter your new password:");
                        String password= getNextLineNotEmpty();
                        if(!Filter.legalPassword(password)){
                            out.println("Invalid password! Only these characters are allowed: 'A-Za-z0-9!@#$%^&*'");
                            continue;
                        }
                        out.println("Repeat your password:");
                        String repeat= getNextLineNotEmpty();
                        if(repeat.equals(password)){
                            user.setPassword(CryptoMD5.md5(password));
                            flagmodify=true;
                        }else {
                            out.println("Your password entered just now does not equal to the password before.");

                        }
                        break;
                    case 2:
                        out.println("Enter your new name:");

                        String name = getNextLineNotEmpty();

                        if (Filter.legalName(name)) {
                            user.setName(name);
                            flagmodify=true;
                        }else{
                            out.println("Invalid name! English name should use '¡¤' instead of space and Chinese name cannot include space or '.'\n");
                            continue;
                        }

                        break;
                    case 3:
                        if(currentUser.getGender()!=-1) {
                            out.println("Sorry, you can't modify your gender :)");
                        }else{
                            out.println("Enter your gender:");
                            String genderbuf=getNextLineNotEmpty();

                            if (Filter.legalGender(genderbuf)) {
                                user.setGender(Integer.parseInt(genderbuf));
                                flagmodify=true;
                                break;
                            } else {
                                out.println("Invalid gender! Gender should be 0(female),1(male) or 2(neutral).");

                            }

                        }
                        break;
                    case 4:
                        out.println("Enter your phone:");
                        String phone=getNextLineNotEmpty();
                        if (Filter.legalName(phone)){
                            user.setPhone(phone);
                            flagmodify=true;
                        } else{
                            out.println("Invalid phone! Phone can only be numbers.");
                        }

                        break;
                    default:
                        out.println("Back to menu...");
                        backtomenu=true;


                }
                if(flagmodify){
                    try {
                        userDao.updateUser(user);
                        out.println("Success! Information updated!");
                        anykeytocontinue();
                        currentUser=user;
                        break;
                    } catch (SQLException e) {
                        out.println("Sorry, we can't update your information due to a database error now!");
                        e.printStackTrace();
                    }
                }else out.println("Your information is keep the same.\n");

            }else {
                out.println("Invalid Option!");

            }
            anykeytocontinue();
        }

    }
    private void launchUserMenu(){
        if(currentUser.getPhone().isEmpty() || currentUser.getGender()==-1 || currentUser.getName().isEmpty()) {
            firstLoginInterface();
        }
        boolean logoutflag=false;
        while (true) {
            if(logoutflag){
                out.printf("Goodbye, %s~\n",currentUser.getUsername());
                currentUser=null;
                anykeytocontinue();
                break;
            }
            out.printf(
                    "Hello, %s.\n" +
                            "What are you going to do?\n" +
                            "1> Check my information and account details\n"+
                            "2> Logout\n"
                    , currentUser.getUsername());
            if(s.hasNextInt()){
                int option=s.nextInt();

                switch (option){
                    case 1:
                        checkInformation();
                        break;
                    case 2:
                        logoutflag=true;
                        break;
                    default:
                        out.println("Invalid Option!");
                }

            }else {
                out.println("Invalid Option!");

            }

        }

    }
    private User login(){

        User u=null;
        out.println(
                "Hey, Welcome to W2OL System.\n" +
                "Who are you?\n"+
                "1> Login\n" +
                "2> Register\n");


        if(s.hasNextInt()){
            int option=s.nextInt();
            switch (option){
                case 1:
                    out.println("Enter your username:");
                    String username=s.next().trim();
                    if(!Filter.legalUsername(username)){
                        out.println("Invalid username! Only these characters are allowed: 'A-Za-z0-9_-'");
                        break;
                    }
                    Console console=System.console();
                    out.println("Enter your password:");
                    String password=s.next().trim();
                    if(!Filter.legalPassword(password)){
                        out.println("Invalid password! Only these characters are allowed: 'A-Za-z0-9!@#$%^&*'");
                        break;
                    }

                    try{
                        u=userDao.login(username,CryptoMD5.md5(password));
                        if(u==null)out.println("Username or Password Incorrect!");
                        else{
                            out.println("Login successfully!");
                        }
                    }catch (SQLException e){
                        out.println("Sorry, we can't help you login to W2OL System due to a database error now!");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    u=register();

                    break;
                default:
                    out.println("Invalid Option!");
            }

        }else {
            out.println("Invalid Option!");

        }
        anykeytocontinue();
        return u;
    }
    public User register(){

        User u=null;

        out.println("Register an unique username:");
        String username=s.next().trim();
        if(!Filter.legalUsername(username)){
            out.println("Invalid username! Only these characters are allowed: 'A-Za-z0-9_-'");
            return u;
        }
        try {
            if(userDao.find(username)!=null){
                out.println("Sorry, this beautiful username has been taken by others.");
                return u;
            }
        }catch (SQLException e){
            out.println("Sorry, we can't help you register in W2OL System due to a database error now!");
            e.printStackTrace();
            return u;
        }


        out.println("Enter your password:");
        String password= s.next().trim();
        if(!Filter.legalPassword(password)){
            out.println("Invalid password! Only these characters are allowed: 'A-Za-z0-9!@#$%^&*'");
            return u;
        }
        out.println("Repeat your password:");
        String repeat= s.next().trim();
        if(repeat.equals(password)){
            try {
                User user=new User(username, CryptoMD5.md5(password));
                userDao.addUser(user);
                u=user;
            }catch (SQLException e){
                out.println("Sorry, we can't help you register in W2OL System due to a database error now!");
                e.printStackTrace();
            }
            out.println("Success! You successfully registered a new account and logged in automatically! Plz remember your login details :)");
            out.println("PS:  You can modify your information later.");
        }else out.println("Your password entered just now does not equal to the password before.");

        return u;
    }

}
