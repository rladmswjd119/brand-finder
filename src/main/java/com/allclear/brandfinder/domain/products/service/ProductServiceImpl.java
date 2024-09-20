package com.allclear.brandfinder.domain.products.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
import com.allclear.brandfinder.domain.products.dto.ProductNoLoginResponse;
import com.allclear.brandfinder.domain.products.entity.Brand;
import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;
import com.allclear.brandfinder.domain.products.entity.Product;
import com.allclear.brandfinder.domain.products.repository.ProductRepository;
import com.allclear.brandfinder.domain.users.entity.Rank;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final int BIRTH_DISCOUNT_RATE = 12;

    private final ProductRepository productRepository;

    @Override
    public Page<Product> getProducts(Pageable pageable) {

        Page<Product> pages = productRepository.findAll(pageable);

        return pages;

    }

    @Override
    public boolean isLogin(UserDetailsImpl userDetails) {
        return userDetails != null;
    }

    @Override
    public Page<ProductLoginResponse> getProductWithLoginList(Page<Product> pages,
                                                                UserDetailsImpl userDetails) {
        double rate = getDiscountRate(userDetails);
        Page<CompletableFuture<ProductLoginResponse>> productLoginResponsePage
                = pages.map(product -> CompletableFuture.supplyAsync(() -> getProductLoginResponse(product, rate)));

        CompletableFuture<Page<ProductLoginResponse>> productLoginResponses
                = changeProductDto(productLoginResponsePage);

        return productLoginResponses.join();
    }

    @Override
    public Page<ProductNoLoginResponse> getProductNoLoginList(Page<Product> pages) {
        Page<CompletableFuture<ProductNoLoginResponse>> productNoLoginResponsePage
                = pages.map(product -> CompletableFuture.supplyAsync(() -> getProductNoLoginResponse(product)));

        CompletableFuture<Page<ProductNoLoginResponse>> productNoLoginResponses
                = changeProductDto(productNoLoginResponsePage);

        return productNoLoginResponses.join();
    }

    private ProductNoLoginResponse getProductNoLoginResponse(Product product) {
        Brand brand = product.getBrand();
        return ProductNoLoginResponse.builder()
                .name(product.getName())
                .productInfo(product.getInformation())
                .brandInfo(brand.getInformation())
                .build();
    }

    private ProductLoginResponse getProductLoginResponse(Product product, double rate) {
        double discountPrice = getDiscountPrice(product.getPrice(), rate);
        Brand brand = product.getBrand();

        return ProductLoginResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .discountPrice(discountPrice)
                .discountRate(rate)
                .productInfo(product.getInformation())
                .brandInfo(brand.getInformation())
                .build();
    }

    private double getDiscountPrice(double price, double rate) {
        double discountPrice = price;
        if(rate > 0) {
            discountPrice *= (rate/100);
        }

        return discountPrice;
    }


    private <T> CompletableFuture<Page<T>> changeProductDto(
                                    Page<CompletableFuture<T>> pages) {

        List<CompletableFuture<T>> list = pages.getContent();
        CompletableFuture<?>[] completableFutureArray = list.toArray(new CompletableFuture<?>[0]);
        CompletableFuture<List<T>> allOfFuture
                = CompletableFuture.allOf(completableFutureArray)
                                        .thenApplyAsync(i -> list.stream()
                                                    .map(CompletableFuture::join)
                                                    .collect(Collectors.toList()));

        return allOfFuture.thenApply(results -> {
                        Pageable pageable = pages.getPageable();
                        return new PageImpl<>(results, pageable, pages.getTotalElements());
        });
    }

    private double getDiscountRate(UserDetailsImpl userDetails) {
        Rank rank = userDetails.getRank();
        double rankRate = rank.getDiscountRate();

        LocalDate now = LocalDate.now();
        if(userDetails.getBirth().equals(now)) {
            rankRate += BIRTH_DISCOUNT_RATE;
        }

        return rankRate;
    }

}
