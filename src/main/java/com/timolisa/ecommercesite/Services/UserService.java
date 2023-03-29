package com.timolisa.ecommercesite.Services;

import com.timolisa.ecommercesite.DTO.UserDTO;
import com.timolisa.ecommercesite.Entity.User;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    User loginUser(UserDTO userDTO);
    User findByEmail(String email);
}
