package com.urbancartel.security;

import com.urbancartel.entity.Seller;
import com.urbancartel.entity.Role;
import com.urbancartel.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(User user){
        return Jwts.builder().
                setSubject(user.getFirstname()).
                claim("roles",user.getRoles().stream().map(Role::getName).toList()).
                claim("username",user.getEmail()).
                claim("id",user.getId()).
                setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis()+1000*60*60)).
                signWith(key).compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().
                setSigningKey(key).
                build().
                parseClaimsJws(token).
                getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }
    public Long extractUserId(String token){
        return extractAllClaims(token).get("id",Long.class);
    }
    public List<String> extractRoles(String token){
        return extractAllClaims(token).get("roles", List.class);
    }
    public boolean validateToken(String token, String username) {
        try {
            return username.equals(extractEmail(token)) && !isTokenExpired(token);
        } catch (Exception e) {
            return false; // Token is invalid
        }

    }
    public boolean isTokenExpired(String token) {

        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(key) // Fixed: added missing signing key
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // If we can't parse, consider it expired
        }
    }
}

