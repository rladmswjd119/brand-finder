package com.allclear.brandfinder.domain.user.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.user.dto.JoinForm;
import com.allclear.brandfinder.domain.user.entity.User;
import com.allclear.brandfinder.domain.user.enums.UserInfoPattern;
import com.allclear.brandfinder.domain.user.repository.UserRepository;
import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(JoinForm form) {
        checkPattern(form);
        checkDup(form);

        String encryptPassword = passwordEncoder.encode(form.getPassword());
        User user = User.builder()
                .username(form.getUsername())
                .password(encryptPassword)
                .email(form.getEmail())
                .birth(form.getBirth())
                .build();

        return userRepository.save(user);
    }

    private void checkPattern(JoinForm form) {
        checkUsernamePattern(form.getUsername());
        checkEmailPattern(form.getEmail());
        checkPasswordPattern(form.getPassword());
    }

    private void checkPasswordPattern(String password) {
        Pattern pattern = Pattern.compile(UserInfoPattern.PASSWORD_PATTERN.getPattern());
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }

    private void checkEmailPattern(String email) {

        Pattern pattern = Pattern.compile(UserInfoPattern.EMAIL_PATTERN.getPattern());
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

    }

    private void checkUsernamePattern(String username) {
        int usernameLen = username.length();
        if(usernameLen < 3 || usernameLen > 20) {
            throw new CustomException(ErrorCode.INVALID_USERNAME_FORMAT);
        }
    }

    private void checkDup(JoinForm form) {
        checkDupUsername(form.getUsername());
        checkDupEmail(form.getEmail());
    }

    public void checkDupEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public void checkDupUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }
    }
}
