package com.allclear.brandfinder.products;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.allclear.brandfinder.domain.products.entity.Product;
import com.allclear.brandfinder.domain.products.repository.ProductRepository;
import com.allclear.brandfinder.domain.products.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getProductsTest() {

        List<Product> list = new ArrayList<>();
        Page<Product> page = new PageImpl<>(list);
        given(productRepository.findAll(pageable)).willReturn(page);

        productService.getProducts(pageable);

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void getProductsByBrandTest() {
        List<Product> list = new ArrayList<>();
        Page<Product> page = new PageImpl<>(list);
        given(productRepository.findAllByBrandId(pageable, 1)).willReturn(page);

        productService.getProductsByBrand(pageable, 1);

        verify(productRepository, times(1)).findAllByBrandId(pageable, 1);
    }

}
