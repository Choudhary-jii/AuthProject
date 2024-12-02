package com.example.CarDetails.Service;

import com.example.CarDetails.Modal.Users;
import com.example.CarDetails.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

import javax.crypto.SecretKey;
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
    //private final String SECRET_KEY = "Z1v6Nk5t2Mhx7G3KXxH5C3R7pBw9Vq6kA8d4TnS2kP1D2g3J9YwG0N5sX9Hb7q4";

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

    private String generateToken(Users user) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("roles", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}