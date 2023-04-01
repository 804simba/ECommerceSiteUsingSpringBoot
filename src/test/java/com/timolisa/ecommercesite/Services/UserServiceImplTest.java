package com.timolisa.ecommercesite.Services;

import com.timolisa.ecommercesite.DTO.UserDTO;
import com.timolisa.ecommercesite.Entity.User;
import com.timolisa.ecommercesite.Repository.UserRepository;
import com.timolisa.ecommercesite.ServiceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        userService = new UserServiceImpl(modelMapper, userRepository);
    }
    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("johndoe@gmail.com");
        userDTO.setPassword("1234");

        userService.saveUser(userDTO);
        User expectedUser = new User("John", "Doe", "john.doe@example.com", "1234");
        Mockito.verify(userRepository).save(expectedUser);
    }
}
