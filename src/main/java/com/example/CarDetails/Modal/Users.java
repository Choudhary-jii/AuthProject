package com.example.CarDetails.Modal;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> role;


    // Default Constructor (Required)
    public Users() {
    }
}

//    @Column(name = "role", unique = true, nullable = false)
//    private List<String> role;


    //    public Users(String username, String email, String password, List<String> role) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
//
//    public Users(Long id,String username, String email, String password, String role) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.role = Collections.singletonList(role);
//    }
//}


//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

//    public void setRole(List<String> role) {
//        this.role = role;
//    }
