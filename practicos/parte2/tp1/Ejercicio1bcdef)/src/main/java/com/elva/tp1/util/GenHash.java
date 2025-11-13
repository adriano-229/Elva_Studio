package com.elva.tp1.util;

public class GenHash {
    public static void main(String[] args) {
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder enc = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(12);
        System.out.println("ADMIN_HASH=" + enc.encode("admin123"));
        System.out.println("USER_HASH=" + enc.encode("user123"));
    }
}
