package com.timolisa.ecommercesite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo<User> extends JpaRepository<User, Long> {

}
