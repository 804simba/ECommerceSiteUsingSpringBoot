package com.timolisa.ecommercesite.Repository;

import com.timolisa.ecommercesite.Entity.Cart;
import com.timolisa.ecommercesite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser_UserID(Long userId);
}
