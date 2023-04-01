package com.timolisa.ecommercesite.Repository;

import com.timolisa.ecommercesite.Entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    void save() {
        // Arrange
        Product product = new Product();
        product.setName("Perfume");
        product.setCategory("Toiletry");
        product.setPrice(new BigDecimal("13.646"));
        product.setDescription("Good");
        product.setId(1L);

        // Act
        Product newProduct = productRepository.save(product);

        // Assert
        assertNotNull(newProduct);
        assertThat(newProduct.getId()).isNotNull();
    }
}
