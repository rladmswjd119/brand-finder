package com.allclear.brandfinder.domain.products.service;

import java.util.List;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;
import com.allclear.brandfinder.domain.products.entity.Product;

public interface ProductService {

    List<Product> getProducts();
    boolean isLogin(UserDetailsImpl userDetails);

    List<ProductLoginResponse> getProductWithLoginList(List<Product> products, UserDetailsImpl userDetails);

}
