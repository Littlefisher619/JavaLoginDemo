package com.littlefisher.hw03.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {

    public static boolean legalUsername(String str){
        String pattern = "^[A-Za-z0-9_\\-]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }
    public static boolean legalPassword(String str){
        String pattern = "^[A-Za-z0-9_!@#$%^&*]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }
    public static boolean legalPhone(String str){
        String pattern = "^[0-9]\\d+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }
    public static boolean legalName(String str){
        String pattern = "^([a-z|A-Z|¡¤]+|[\\u4e00-\\u9fa5]+)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }
    public static boolean legalGender(Integer gender){
        if(gender>2 || gender<0) return false;
        else return true;
    }
    public static boolean legalGender(String gender){
        String pattern = "^(0|1|2)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(gender);
        return m.matches();
    }
}
