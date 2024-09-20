package com.allclear.brandfinder.products;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.allclear.brandfinder.domain.auth.util.AccessTokenUtil;
import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;
import com.allclear.brandfinder.domain.products.dto.ProductNoLoginResponse;
import com.allclear.brandfinder.domain.users.dto.JoinForm;
import com.allclear.brandfinder.global.response.SuccessResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AccessTokenUtil accessTokenUtil;

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
    public void 상품목록_로그인_없이_조회_테스트() {

        ResponseEntity<SuccessResponse<List<ProductLoginResponse>>> responseEntity
                = testRestTemplate.exchange("/api/products/", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});


        List<ProductLoginResponse> result = responseEntity.getBody().getData();

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    public void 상품목록_로그인_조회_테스트() {

        HttpHeaders httpHeaders = new HttpHeaders();
        String token = accessTokenUtil.createAccessToken(form.getUsername());
        httpHeaders.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<SuccessResponse<List<ProductNoLoginResponse>>> responseEntity
                = testRestTemplate.exchange("/api/products/", HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {});


        List<ProductNoLoginResponse> result = responseEntity.getBody().getData();

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    public void 브랜드별_상품목록_조회_로그인_없는_테스트() {

        ResponseEntity<SuccessResponse<List<ProductNoLoginResponse>>> responseEntity
                = testRestTemplate.exchange("/api/products/brands/" + 4, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});


        List<ProductNoLoginResponse> result = responseEntity.getBody().getData();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 브랜드별_상품목록_조회_로그인_테스트() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = accessTokenUtil.createAccessToken(form.getUsername());
        httpHeaders.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<SuccessResponse<List<ProductNoLoginResponse>>> responseEntity
                = testRestTemplate.exchange("/api/products/brands/" + 4, HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {});


        List<ProductNoLoginResponse> result = responseEntity.getBody().getData();

        assertThat(result.size()).isEqualTo(2);
    }

}
