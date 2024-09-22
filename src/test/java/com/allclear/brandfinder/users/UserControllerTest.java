package com.allclear.brandfinder.users;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.allclear.brandfinder.domain.users.dto.JoinForm;
import com.allclear.brandfinder.domain.users.dto.LoginForm;
import com.allclear.brandfinder.domain.users.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    public TestRestTemplate testRestTemplate;

    private JoinForm form;

    @BeforeEach
    public void setUp(){
        form = JoinForm.builder()
                .username("username")
                .password("password1234!!")
                .email("rladmswjd23@naver.com")
                .birth(LocalDate.of(1997,1,19))
                .build();

        HttpEntity<JoinForm> entity = new HttpEntity<>(form);
        ResponseEntity<String> result = testRestTemplate.exchange("/api/users/join", HttpMethod.POST, entity, String.class);
    }

    @Test
    public void 로그인_테스트() {

        LoginForm loginForm = LoginForm.builder()
                                        .username(form.getUsername())
                                        .password(form.getPassword())
                                        .build();

        HttpEntity<LoginForm> entity = new HttpEntity<>(loginForm);
        ResponseEntity<String> result = testRestTemplate.exchange("/api/users/login", HttpMethod.POST, entity, String.class);
    }
}
