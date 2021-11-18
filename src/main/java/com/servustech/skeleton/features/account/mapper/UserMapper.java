package com.servustech.skeleton.features.account.mapper;

import com.servustech.skeleton.features.account.User;
import com.servustech.skeleton.security.payload.RegisterRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    User signUpRequestToUser(RegisterRequest registerRequest);

    User signUpRequestToLecturer(RegisterRequest registerRequest);

    User signUpRequestToPremium(RegisterRequest registerRequest);

}
