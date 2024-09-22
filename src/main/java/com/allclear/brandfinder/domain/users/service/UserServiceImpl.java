package com.allclear.brandfinder.domain.users.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.auth.service.AuthService;
import com.allclear.brandfinder.domain.users.dto.JoinForm;
import com.allclear.brandfinder.domain.users.dto.LoginForm;
import com.allclear.brandfinder.domain.users.entity.Rank;
import com.allclear.brandfinder.domain.users.entity.User;
import com.allclear.brandfinder.domain.users.enums.RankEnum;
import com.allclear.brandfinder.domain.users.enums.UserInfoPattern;
import com.allclear.brandfinder.domain.users.repository.RankRepository;
import com.allclear.brandfinder.domain.users.repository.UserRepository;
import com.allclear.brandfinder.global.config.SecurityConfig;
import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final SecurityConfig securityConfig;

    @Override
    public User signUp(JoinForm form) {
        checkPattern(form);
        checkDup(form);

        String encryptPassword = passwordEncoder.encode(form.getPassword());
        Rank rank = rankRepository.findByName(RankEnum.GENERAL.getValue());
        User user = User.builder()
                .username(form.getUsername())
                .password(encryptPassword)
                .email(form.getEmail())
                .birth(form.getBirth())
                .rank(rank)
                .build();

        log.info("회원 가입 요청 : {}, {}, {}, {}", user.getUsername(),
                                                user.getPassword(),
                                                user.getEmail(),
                                                user.getBirth());

        return userRepository.save(user);
    }

    @Override
    public HttpHeaders signIn(LoginForm form) {
        User user = findUsername(form.getUsername());
        if(user == null) {
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        verifyPassword(user.getPassword(), form.getPassword());

        return authService.createHttpHeaders(form.getUsername());
    }

    private void verifyPassword(String password, String rowPassword) {
        if(!password.equals(securityConfig.passwordEncoder().encode(rowPassword))) {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    private void checkPattern(JoinForm form) {
        checkUsernamePattern(form.getUsername());
        checkEmailPattern(form.getEmail());
        checkPasswordPattern(form.getPassword());
    }

    private void checkPasswordPattern(String password) {
        int passwordLen = password.length();
        Pattern pattern = Pattern.compile(UserInfoPattern.PASSWORD_PATTERN.getPattern());
        Matcher matcher = pattern.matcher(password);

        if((passwordLen < 8 || passwordLen > 20) && !matcher.matches()) {
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
        User userByUsername = findUsername(form.getUsername());
        if(userByUsername != null) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        User userByEmail = findEmail(form.getEmail());
        if(userByEmail != null) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public User findEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
