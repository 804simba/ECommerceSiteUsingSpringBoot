package com.timolisa.ecommercesite.Controller;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;
    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index";
    }
}
