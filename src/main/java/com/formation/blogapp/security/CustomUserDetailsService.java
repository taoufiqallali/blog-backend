package com.formation.blogapp.security;

import com.formation.blogapp.domain.User;
import com.formation.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // Return CustomUserDetails instead of Spring's User
        return new CustomUserDetails(
                user.getId(),                           // Add user ID
                user.getEmail(),
                user.getPasswordHash(),
                "ROLE_" + user.getRole().name()
        );
    }
}