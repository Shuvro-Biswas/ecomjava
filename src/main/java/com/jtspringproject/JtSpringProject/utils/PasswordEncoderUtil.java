package com.jtspringproject.JtSpringProject.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword1 = "1999Shuvrobiswas&$";
        String rawPassword2 = "123";

        System.out.println("Hashed shuvro password: " + encoder.encode(rawPassword1));
        System.out.println("Hashed admin password: " + encoder.encode(rawPassword2));
    }
}

