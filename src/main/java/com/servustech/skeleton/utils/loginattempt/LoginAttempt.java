package com.servustech.skeleton.utils.loginattempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAttempt {
    private int nr;
    private LocalDateTime localDateTime;
}
