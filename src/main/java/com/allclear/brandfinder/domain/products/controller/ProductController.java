package com.allclear.brandfinder.domain.products.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;
import com.allclear.brandfinder.domain.products.dto.ProductNoLoginResponse;
import com.allclear.brandfinder.domain.products.dto.ProductResponse;
import com.allclear.brandfinder.domain.products.entity.Product;
import com.allclear.brandfinder.domain.products.service.ProductService;
import com.allclear.brandfinder.global.response.SuccessResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public SuccessResponse<List<? extends ProductResponse>> getProducts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(sort = "score", direction = Sort.Direction.DESC)Pageable pageable) {

        Page<Product> page = productService.getProducts(pageable);
        if(productService.isLogin(userDetails)) {
            log.info("로그인을 하셨습니다.");

            Page<ProductLoginResponse> productLoginResponsePage = productService.getProductWithLoginList(page, userDetails);
            List<ProductLoginResponse> productLoginResponses = productLoginResponsePage.getContent();
            return SuccessResponse.withData(productLoginResponses);
        }

        log.info("로그인을 해주세요.");
        Page<ProductNoLoginResponse> productNoLoginResponsePage = productService.getProductNoLoginList(page);
        List<ProductNoLoginResponse> productLoginResponses = productNoLoginResponsePage.getContent();
        return SuccessResponse.withData(productLoginResponses);
    }
}
