package com.timolisa.ecommercesite.ServiceImpl;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Entity.Product;
import com.timolisa.ecommercesite.Exception.ProductNotFoundException;
import com.timolisa.ecommercesite.Repository.ProductRepo;
import com.timolisa.ecommercesite.Services.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findProductById(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return modelMapper.map(product, ProductDTO.class);
        } else {
            String message = String.format("Product with ID: %d, not found", id);
            throw new ProductNotFoundException(message);
        }
    }

    @Override
    public ProductDTO saveAndFlush(ProductDTO productDTO) {
        BigDecimal price = new BigDecimal(productDTO.getPrice());

        // Create a new Product object with the converted price
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(price);
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImageURL(productDTO.getImageURL());
        System.out.println("Product i want to save" + product);
        product = productRepo.save(product);
        System.out.println("Product saved");
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public boolean deleteByID(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
