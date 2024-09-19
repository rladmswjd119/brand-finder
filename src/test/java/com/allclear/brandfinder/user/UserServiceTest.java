package com.allclear.brandfinder.user;

import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.allclear.brandfinder.domain.auth.service.AuthService;
import com.allclear.brandfinder.domain.user.dto.JoinForm;
import com.allclear.brandfinder.domain.user.dto.LoginForm;
import com.allclear.brandfinder.domain.user.entity.User;
import com.allclear.brandfinder.domain.user.repository.UserRepository;
import com.allclear.brandfinder.domain.user.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    public UserRepository userRepository;

    @Mock
    public PasswordEncoder passwordEncoder;

    @Mock
    private AuthService authService;

    @InjectMocks
    public UserServiceImpl userService;

    @Test
    public void signUpTest() {
        JoinForm form = JoinForm.builder()
                .username("username")
                .password("password1234!!")
                .email("rladmswjd23@naver.com")
                .birth(LocalDate.of(1997,1,19))
                .build();
        User user = mock(User.class);
        given(userRepository.findByUsername(anyString())).willReturn(null);
        given(userRepository.findByEmail(anyString())).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        userService.signUp(form);

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void signInTest(){
        LoginForm form = LoginForm.builder()
                .username("username")
                .password("password2323")
                .build();
        User user = mock(User.class);
        given(userRepository.findByUsername(anyString())).willReturn(user);

        userService.signIn(form);

        verify(userRepository, times(1)).findByUsername(anyString());
    }

}
