package com.timolisa.ecommercesite.Entity;

import com.timolisa.ecommercesite.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userID;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "email", columnDefinition = "TEXT", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    private String salt;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, salt);
    }
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
