package com.formation.blogapp.service;

import com.formation.blogapp.dto.Response.UserResponse;
import com.formation.blogapp.dto.request.UpdateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public UserResponse updateUser(UpdateUserRequest updateUserRequest);

    public UserResponse getById(Long id);

    public UserResponse getByEmail(String email);

    public Page<UserResponse> getUsers(Pageable pageable);

    public void deleteUser(Long id);



}
