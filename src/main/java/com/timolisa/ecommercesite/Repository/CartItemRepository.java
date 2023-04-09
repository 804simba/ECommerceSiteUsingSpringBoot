package com.timolisa.ecommercesite.Repository;

import com.timolisa.ecommercesite.Entity.Cart;
import com.timolisa.ecommercesite.Entity.CartItem;
import com.timolisa.ecommercesite.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findCartItemByCartAndProduct(Cart cart, Product product);
    List<CartItem> findCartItemByCart(Cart cart);
}
