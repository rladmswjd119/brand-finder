package com.allclear.brandfinder.domain.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allclear.brandfinder.domain.products.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
