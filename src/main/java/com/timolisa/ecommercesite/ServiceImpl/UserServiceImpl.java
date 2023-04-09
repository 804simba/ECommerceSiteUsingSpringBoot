package com.timolisa.ecommercesite.ServiceImpl;

import com.timolisa.ecommercesite.DTO.UserDTO;
import com.timolisa.ecommercesite.Entity.User;
import com.timolisa.ecommercesite.Repository.UserRepository;
import com.timolisa.ecommercesite.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User(userDTO.getFirstName(),
                userDTO.getLastName(), userDTO.getEmail(),
                userDTO.getPassword());
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User loginUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        User user = findByEmail(email);
        if (user != null &&
            user.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }
}
