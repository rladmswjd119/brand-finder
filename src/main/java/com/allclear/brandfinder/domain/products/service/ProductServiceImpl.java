package com.allclear.brandfinder.domain.products.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
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
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean isLogin(UserDetailsImpl userDetails) {
        return userDetails != null;
    }

    @Override
    public List<ProductLoginResponse> getProductWithLoginList(List<Product> products,
                                                                UserDetailsImpl userDetails) {
        double rate = getDiscountRate(userDetails);

        List<CompletableFuture<ProductLoginResponse>> listProductLoginResponse = new ArrayList<>();
        for(Product product : products) {
            listProductLoginResponse.add(CompletableFuture.supplyAsync(
                                            () -> getProductLoginResponse(product, rate)));
        }

        CompletableFuture<List<ProductLoginResponse>> productLoginResponses
                = changeProductLoginResponses(listProductLoginResponse);

        return productLoginResponses.join();
    }

    private ProductLoginResponse getProductLoginResponse(Product product, double rate) {
        double discountPrice = getDiscountPrice(product.getPrice(), rate);
        Brand brand = product.getBrand();

        return ProductLoginResponse.builder()
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


    private CompletableFuture<List<ProductLoginResponse>> changeProductLoginResponses(
                                    List<CompletableFuture<ProductLoginResponse>> listProductLoginResponse) {

        CompletableFuture<?>[] completableFutureArray = listProductLoginResponse.toArray(new CompletableFuture<?>[0]);
        return CompletableFuture.allOf(completableFutureArray)
                .thenApplyAsync(i -> listProductLoginResponse.stream()
                                                                .map(CompletableFuture::join)
                                                                .collect(Collectors.toList()));

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
