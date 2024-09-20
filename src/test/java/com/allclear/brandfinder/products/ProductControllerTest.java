package com.allclear.brandfinder.products;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private TestRestTemplate testRestTemplate;

    @Test
    public void 상품목록_로그인_조회_테스트() {

        ResponseEntity<List<ProductLoginResponse>> responseEntity
                = testRestTemplate.exchange("/api/produts", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProductLoginResponse>>() {});

        List<ProductLoginResponse> result = responseEntity.getBody();

        assertThat(result.size()).isEqualTo(10);
    }

}
