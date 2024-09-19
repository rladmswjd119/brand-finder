package com.allclear.brandfinder.products;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.allclear.brandfinder.domain.products.repository.ProductRepository;
import com.allclear.brandfinder.domain.products.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getProductsWithLoginTest() {
        productService.getProducts();

        verify(productRepository, times(1)).findAll();
    }

}
