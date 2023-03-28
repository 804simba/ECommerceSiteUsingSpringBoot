package com.timolisa.ecommercesite.Repository;

import com.timolisa.ecommercesite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserID(Long id);
    User findUserByEmail(String email);
}
