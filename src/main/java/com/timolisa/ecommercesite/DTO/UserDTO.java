package com.timolisa.ecommercesite.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

