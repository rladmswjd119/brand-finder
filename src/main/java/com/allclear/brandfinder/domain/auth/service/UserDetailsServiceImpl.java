package com.allclear.brandfinder.domain.auth.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
import com.allclear.brandfinder.domain.users.dto.JoinForm;
import com.allclear.brandfinder.domain.users.entity.User;
import com.allclear.brandfinder.domain.users.repository.UserRepository;
import com.allclear.brandfinder.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByUsername(username))
                .orElseThrow(() -> new NullPointerException(ErrorCode.ACCOUNT_NOT_FOUND.getMessage()));

        log.info("{}의 등급은 {} 입니다.", user.getUsername(), user.getRank().getName());

        return new UserDetailsImpl(user);
    }

}
