package com.timolisa.ecommercesite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo<User> extends JpaRepository<User, Long> {

}
