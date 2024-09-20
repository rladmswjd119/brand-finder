package com.allclear.brandfinder.domain.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
import com.allclear.brandfinder.domain.users.entity.User;
import com.allclear.brandfinder.domain.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByUsername(username))
                .orElseThrow(() -> new RuntimeException("가입되지 않은 계정입니다."));

        return new UserDetailsImpl(user);
    }

}
