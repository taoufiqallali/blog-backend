package com.formation.blogapp.service.impl;


import com.formation.blogapp.domain.User;
import com.formation.blogapp.dto.Response.UserResponse;
import com.formation.blogapp.dto.request.UpdateUserRequest;
import com.formation.blogapp.exception.UserNotFoundException;
import com.formation.blogapp.mapper.UserMapper;
import com.formation.blogapp.repository.UserRepository;
import com.formation.blogapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Transactional
    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();


        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new UserNotFoundException(currentEmail));

        user.setFirstname(updateUserRequest.firstname());
        user.setLastname(updateUserRequest.lastname());

        return  mapper.toDto(user);
    }

    @Transactional
    @Override
    public UserResponse getById(Long id){
        return mapper.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Transactional
    @Override
    public UserResponse getByEmail(String email) {
        return mapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email)));
    }

    @Override
    @Transactional
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public void deleteUser(Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User target = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isSelf = target.getEmail().equals(email);

        if (!isAdmin && !isSelf) {
            throw new AccessDeniedException("You are not allowed to delete this user");
        }

        userRepository.delete(target);
    }


}
