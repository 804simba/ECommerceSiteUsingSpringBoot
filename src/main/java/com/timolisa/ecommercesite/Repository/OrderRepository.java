package com.timolisa.ecommercesite.Repository;

import com.timolisa.ecommercesite.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
