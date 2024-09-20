package com.allclear.brandfinder.products;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;
import com.allclear.brandfinder.domain.products.dto.ProductNoLoginResponse;
import com.allclear.brandfinder.domain.products.dto.ProductResponse;
import com.allclear.brandfinder.global.response.SuccessResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void 상품목록_로그인_없는_조회_테스트() {

        ResponseEntity<SuccessResponse<List<ProductNoLoginResponse>>> responseEntity
                = testRestTemplate.exchange("/api/products/", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});


        List<ProductNoLoginResponse> result = responseEntity.getBody().getData();

        assertThat(result.size()).isEqualTo(10);
    }

}
