package com.payroll.util;


import org.apache.commons.codec.digest.DigestUtils;


public class PasswordUtil {
    public static String hashPassword(String plain) {
        return DigestUtils.sha256Hex(plain);
    }


    public static boolean verify(String plain, String hash) {
        return hashPassword(plain).equals(hash);
    }
}