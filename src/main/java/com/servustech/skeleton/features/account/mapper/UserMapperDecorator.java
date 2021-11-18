package com.servustech.skeleton.features.account.mapper;

import com.servustech.skeleton.features.account.AccountStatus;
import com.servustech.skeleton.features.account.User;
import com.servustech.skeleton.features.account.role.RoleService;
import com.servustech.skeleton.security.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;


    @Override
    public User signUpRequestToUser(RegisterRequest registerRequest) {
        User user = userMapper.signUpRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRoles(Collections.singleton(roleService.getUserRole()));
        return user;
    }

    @Override
    public User signUpRequestToLecturer(RegisterRequest registerRequest) {
        User user = userMapper.signUpRequestToLecturer(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAccountStatus(AccountStatus.INACTIVE);
        user.setRoles(Collections.singleton(roleService.getLecturerRole()));
        return user;
    }
    @Override
    public User signUpRequestToPremium(RegisterRequest registerRequest) {
        User user = userMapper.signUpRequestToPremium(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAccountStatus(AccountStatus.INACTIVE);
        user.setRoles(Collections.singleton(roleService.getPremiumRole()));
        return user;
    }
}
