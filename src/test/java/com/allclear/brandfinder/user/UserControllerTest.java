package com.allclear.brandfinder.user;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.allclear.brandfinder.domain.user.dto.JoinForm;
import com.allclear.brandfinder.domain.user.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    public UserService userService;

    @Autowired
    public TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp(){
        JoinForm form = JoinForm.builder()
                .username("username")
                .password("password1234!!")
                .email("rladmswjd23@naver.com")
                .birth(LocalDate.of(1997,1,19))
                .build();

        HttpEntity<JoinForm> entity = new HttpEntity<>(form);
        ResponseEntity<String> result = testRestTemplate.exchange("/api/users/join", HttpMethod.POST, entity, String.class);
    }
}
