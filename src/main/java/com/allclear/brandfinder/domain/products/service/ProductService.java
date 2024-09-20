package com.allclear.brandfinder.domain.products.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.allclear.brandfinder.domain.auth.dto.UserDetailsImpl;
import com.allclear.brandfinder.domain.products.dto.ProductLoginResponse;
import com.allclear.brandfinder.domain.products.dto.ProductNoLoginResponse;
import com.allclear.brandfinder.domain.products.entity.Product;

public interface ProductService {

    Page<Product> getProducts(Pageable pageable);

    boolean isLogin(UserDetailsImpl userDetails);

    Page<ProductLoginResponse> getProductWithLoginList(Page<Product> products, UserDetailsImpl userDetails);

    Page<ProductNoLoginResponse> getProductNoLoginList(Page<Product> products);

    Page<Product> getProductsByBrand(Pageable pageable, int brandId);

}
