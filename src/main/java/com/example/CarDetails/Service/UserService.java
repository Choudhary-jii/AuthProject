package com.example.CarDetails.Service;

import com.example.CarDetails.Modal.Users;
import com.example.CarDetails.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public void saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Set.of("USER"));
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsernameOrEmail(username).isPresent();
    }

    public String authenticateUser(Users user) {
        Users existingUser = userRepository.findByUsernameOrEmail(user.getUsername())
                .or(() -> userRepository.findByEmail(user.getEmail())) // Handle email login
                .orElse(null);
        if (existingUser != null && new BCryptPasswordEncoder().matches(user.getPassword(), existingUser.getPassword())) {
            return generateToken(existingUser);
        }
        return null;
    }

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameOrEmail(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert roles to GrantedAuthority
        Collection<GrantedAuthority> authorities = user.getRole().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet()); // Use a Set to satisfy Collection requirement

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

//    private String generateToken(Users user) {
//        if (user == null) {
//            throw new IllegalArgumentException("User cannot be null");
//        }
//        Claims claims = Jwts.claims().setSubject(user.getUsername());
//        claims.put("userId", user.getId());
//        claims.put("roles", user.getRole());
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
private String generateToken(Users user) {
    if (user == null) {
        throw new IllegalArgumentException("User cannot be null");
    }
    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("userId", user.getId());

    // Add ROLE_ prefix to roles
    claims.put("roles", user.getRole().stream()
            .map(role -> "ROLE_" + role)
            .collect(Collectors.toSet()));

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
}

}
