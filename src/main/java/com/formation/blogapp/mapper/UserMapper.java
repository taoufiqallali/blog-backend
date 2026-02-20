package com.formation.blogapp.mapper;

import com.formation.blogapp.domain.User;
import com.formation.blogapp.dto.Response.UserResponse;
import com.formation.blogapp.dto.request.RegisterRequest;
import com.formation.blogapp.dto.request.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "passwordHash", ignore = true)
    public User toEntity(RegisterRequest registerRequest);

    @Mapping(target = "passwordHash", ignore = true)
    public User toEntity(UpdateUserRequest updateUserRequest);

    public UserResponse toDto(User user);

}
