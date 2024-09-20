package com.allclear.brandfinder.domain.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class ProductLoginResponse extends ProductResponse {
    private double price;
    private double discountPrice;
    private double discountRate;

}
