package com.allclear.brandfinder.domain.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductLoginResponse {
    private int price;
    private int discountPrice;
    private int discountRate;
    private String productInfo;
    private String brandInfo;
}
