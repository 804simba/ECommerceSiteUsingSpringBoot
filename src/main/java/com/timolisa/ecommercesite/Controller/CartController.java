package com.timolisa.ecommercesite.Controller;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Entity.*;
import com.timolisa.ecommercesite.Exception.ProductNotFoundException;
import com.timolisa.ecommercesite.Repository.CartItemRepository;
import com.timolisa.ecommercesite.Repository.CartRepository;
import com.timolisa.ecommercesite.Repository.OrderRepository;
import com.timolisa.ecommercesite.Repository.UserRepository;
import com.timolisa.ecommercesite.Services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService,
                          CartRepository cartRepository,
                          CartItemRepository cartItemRepository,
                          UserRepository userRepository,
                          OrderRepository orderRepository,
                          ModelMapper modelMapper) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) throws ProductNotFoundException {
        Long userId = (Long) session.getAttribute("userId");
        Cart cart = cartRepository.findCartByUser_UserID(userId);

        if (cart == null) {
            cart = new Cart();
            User user = userRepository.findById(userId).orElse(null);
            cart.setUser(user);
            cartRepository.save(cart);
        }

        Product product = modelMapper.map(productService.findProductById(id), Product.class);

        CartItem cartItem = cartItemRepository.findCartItemByCartAndProduct(cart, product);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(cartItem.getQuantity() + quantity);

        cartItemRepository.save(cartItem);

        return "redirect:/home";
    }

    @GetMapping
    public ModelAndView viewCart(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Cart cart = cartRepository.findCartByUser_UserID(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElse(null));
        }

        List<CartItem> cartItems = cartItemRepository.findCartItemByCart(cart);

        Map<ProductDTO, Integer> cartItemMap = new HashMap<>();

        for (CartItem cartItem : cartItems) {
            ProductDTO productDTO = new ProductDTO(cartItem.getProduct());
            cartItemMap.put(productDTO, cartItem.getQuantity());
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("cart");
        mav.addObject("cartItems", cartItemMap);

        return mav;
    }

    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("productId") Long productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) throws ProductNotFoundException {
        Long userId = (Long) session.getAttribute("userId");
        Cart cart = cartRepository.findCartByUser_UserID(userId);

        if (cart == null) {
            return "redirect:/cart";
        }

        Product product = modelMapper.map(productService
                        .findProductById(productId), Product.class);

        CartItem cartItem = cartItemRepository.findCartItemByCartAndProduct(cart, product);

        if (cartItem == null) {
            return "redirect:/cart";
        }

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Cart cart = cartRepository.findCartByUser_UserID(userId);

        if (cart == null) {
            return "redirect:/cart";
        }

        Order order = new Order();
        User user = userRepository.findById(userId).orElse(null);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setSubtotal(cart.getTotalPrice());
        order.setCart(cart);
        order.setProducts(cart.getCartItems().stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toSet()));

        orderRepository.save(order);

        cart.setCartItems(new HashSet<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return "orders";
    }
}
