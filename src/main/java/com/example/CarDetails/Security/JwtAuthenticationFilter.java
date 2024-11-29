////package com.example.CarDetails.Security;
////
////import com.example.CarDetails.Service.UserService;
////import jakarta.servlet.FilterChain;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.GrantedAuthority;
////import org.springframework.security.core.authority.SimpleGrantedAuthority;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
////import org.springframework.stereotype.Component;
////import org.springframework.web.filter.OncePerRequestFilter;
////import io.jsonwebtoken.Jwts;
////
////import java.io.IOException;
////import java.util.Collection;
////import java.util.Date;
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Component
////public class JwtAuthenticationFilter extends OncePerRequestFilter {
////
////    private final UserService userService;
////
////    @Value("${jwt.secret}")
////    private String SECRET_KEY;
////
////    @Autowired
////    public JwtAuthenticationFilter(UserService userService) {
////        this.userService = userService;
////    }
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
////            throws ServletException, IOException {
////        final String authHeader = request.getHeader("Authorization");
////
////        String username = null;
////        String jwt = null;
////
////        if (authHeader != null && authHeader.startsWith("Bearer ")) {
////            jwt = authHeader.substring(7);
////            username = extractUsername(jwt);
////        }
////
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            UserDetails userDetails = userService.loadUserByUsername(username);
////
////            if (validateToken(jwt, userDetails)) {
////                // Extract roles from JWT and set them as authorities
////                List<String> roles = (List<String>) Jwts.parserBuilder()
////                        .setSigningKey(SECRET_KEY)
////                        .build()
////                        .parseClaimsJws(jwt)
////                        .getBody()
////                        .get("roles", List.class);
////
////                Collection<GrantedAuthority> authorities = roles.stream()
////                        .map(SimpleGrantedAuthority::new)
////                        .collect(Collectors.toSet());
////
////                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
////                        userDetails, null, authorities);
////                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////                SecurityContextHolder.getContext().setAuthentication(auth);
////            }
////        }
////
////        chain.doFilter(request, response);
////    }
////
////    private String extractUsername(String jwt) {
////        return Jwts.parserBuilder()
////                .setSigningKey(SECRET_KEY)
////                .build()
////                .parseClaimsJws(jwt)
////                .getBody()
////                .getSubject();
////    }
////
////    private boolean validateToken(String token, UserDetails userDetails) {
////        final String username = extractUsername(token);
////        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
////    }
////
////    private boolean isTokenExpired(String token) {
////        return extractExpiration(token).before(new Date());
////    }
////
////    private Date extractExpiration(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKey(SECRET_KEY)
////                .build()
////                .parseClaimsJws(token)
////                .getBody()
////                .getExpiration();
////    }
////}
//package com.example.CarDetails.Security;
//
//import com.example.CarDetails.Service.UserService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    private final UserService userService;
//
//    @Autowired
//    public JwtAuthenticationFilter(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7);
//            username = extractUsername(jwt);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userService.loadUserByUsername(username);
//
//            if (validateToken(jwt, userDetails)) {
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(secretKey)
//                        .build()
//                        .parseClaimsJws(jwt)
//                        .getBody();
//
//                List<String> roles = claims.get("roles", List.class);
//                Collection<GrantedAuthority> authorities = roles.stream()
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, authorities);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    private String extractUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    private boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        Date expiration = Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//        return expiration.before(new Date());
//    }
//}
package com.example.CarDetails.Security;

import com.example.CarDetails.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/auth/login") || request.getRequestURI().startsWith("/auth/signup")) {
            chain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (validateToken(jwt, userDetails)) {
                Claims claims = Jwts.parserBuilder()
                        // Use MacAlgorithm.HS256 to get the signing key
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

//               List<String> roles = claims.get("roles", List.class);
//                Collection<GrantedAuthority> authorities = roles.stream()
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
                Collection<GrantedAuthority> authorities = claims.get("roles", List.class).stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toSet());


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    private String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    // Helper method to get the signing key using HS256 algorithm
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
