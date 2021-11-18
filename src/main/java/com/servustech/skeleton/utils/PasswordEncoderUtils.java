package com.servustech.skeleton.utils;

import com.servustech.skeleton.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncoderUtils {

    @Autowired
    private static BCryptPasswordEncoder passwordEncoder;


    public static void verifyMatchingPasswords(String oldPassword, String currentPassword) {
        if (!passwordEncoder.matches(oldPassword, currentPassword)) {
            throw new CustomException("Invalid old password");
        }
    }

    public static String encode(String password){
        return passwordEncoder.encode(password);
    }

}
