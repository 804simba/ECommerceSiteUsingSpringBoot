package com.timolisa.ecommercesite.Controller;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Exception.ProductNotFoundException;
import com.timolisa.ecommercesite.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    ProductService productService;
    private final Map<Long, Integer> cart = new HashMap<>();
    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            @RequestParam(name = "quantity", defaultValue = "1") int quantity) throws ProductNotFoundException {
        if (cart.containsKey(id)) {
            cart.put(id, cart.get(id) + quantity);
        } else {
            cart.put(id, quantity);
        }
        return "redirect:/home";
    }
    @GetMapping
    public ModelAndView viewCart() throws ProductNotFoundException {
        Map<ProductDTO, Integer> cartItems = new HashMap<>();
        for (Long productId : cart.keySet()) {
            ProductDTO product = productService.findProductById(productId);
            int quantity = cart.get(productId);
            cartItems.put(product, quantity);
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cart");
        mav.addObject("cartItems", cartItems);
        return mav;
    }
}
