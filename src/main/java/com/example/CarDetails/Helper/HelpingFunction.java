package com.example.CarDetails.Helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class HelpingFunction {

    //@Value("${jwt.secret.key}")
    private final String SECRET_KEY = "Z1v6Nk5t2Mhx7G3KXxH5C3R7pBw9Vq6kA8d4TnS2kP1D2g3J9YwG0N5sX9Hb7q4";

    SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

}