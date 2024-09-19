package com.allclear.brandfinder.domain.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.products.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Override
    public List<Product> getProducts() {

        return null;
    }

}
