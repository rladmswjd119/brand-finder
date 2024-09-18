package com.allclear.brandfinder.user;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.allclear.brandfinder.domain.user.entity.User;
import com.allclear.brandfinder.domain.user.repository.UserRepository;
import com.allclear.brandfinder.domain.user.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    public UserRepository userRepository;

    @InjectMocks
    public UserServiceImpl userService;

    @Test
    public void signUpTest() {
        User user = mock(User.class);
        given(userRepository.findByUsername(anyString())).willReturn(null);
        given(userRepository.findByEmail(anyString())).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        User result = userService.signUp(any());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any());
    }

}
