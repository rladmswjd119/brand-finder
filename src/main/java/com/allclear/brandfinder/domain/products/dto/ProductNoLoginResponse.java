package com.allclear.brandfinder.domain.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductNoLoginResponse {
    private String productInfo;
    private String brandInfo;
}
