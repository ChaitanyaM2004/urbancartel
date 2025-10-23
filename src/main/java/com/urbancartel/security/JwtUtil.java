package com.urbancartel.security;

import com.urbancartel.entity.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;


//What does jwt builder does
//
//This method creates a JWT token (a secure string) for a given user.
//Step by step:
//        Jwts.builder()
//→ Start building a new JWT token.
//        .setSubject(user.getFirstname())
//        → The main subject/identity of the token = user’s first name.
//.claim("roles", user.getRoles()....)
//        → Add extra info (custom data) into the token.
//Here, it adds the user’s roles (like ADMIN, USER).
//A claim = a piece of information inside the token.
//        .claim("username", user.getEmail())
//        → Add user’s email as username.
//        .claim("id", user.getId())
//        → Add user’s database ID.
//.setIssuedAt(new Date())
//        → Record the time when the token was created.
//        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//        → Set token’s expiry time = current time + 1 hour.
//        (1000 ms × 60 sec × 60 min = 1 hour)
//        .signWith(key)
//→ Sign (lock) the token using the secret key.
//This ensures nobody can modify the token without knowing the key.
//.compact()
//→ Finally, build the token as a compact string (the actual JWT).
//
//
//1️⃣ What is a “compact string” in JWT and what is the struct of jwt?
//A JWT token has 3 parts:
//Header → info about algorithm
//Payload → the claims (like id, username, roles)
//Signature → signed using the secret key
//Example of JWT structure:
//xxxxx.yyyyy.zzzzz
//Each part is Base64 encoded and joined by dots (.).
//This final string is called the compact string.
//It’s what you send in HTTP headers like:
//Authorization: Bearer <JWT-token-string>
//So “compact string” = the single string version of the JWT that contains all info + signature.
//2️⃣ What is 1000 * 60 * 60?
//This is used to calculate milliseconds for expiry time.
//        System.currentTimeMillis() → current time in milliseconds
//1 second = 1000 milliseconds
//1 minute = 60 seconds
//1 hour = 60 minutes
//So:
//        1000 (ms) * 60 (sec) * 60 (min) = 3,600,000 ms = 1 hour
//When you do:
//        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
//        → You are setting the token to expire 1 hour from now.
//✅ In simplest words:
//Compact string → the full JWT token in a single string you can send over the internet.
//        1000 * 60 * 60 → math to convert 1 hour → milliseconds (because Java Date uses milliseconds).


//Step by step explanation:
//extractAllClaims(token)
//Calls the method we just discussed.
//It parses the token using the secret key and returns all the data inside it (the claims).
//        .get("email", String.class)
//Picks out the claim named "email" from the token.
//        String.class tells Java: “I expect this data to be a String.”
@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);//this is from jjwt library java jwt
    //it will generate random key for us
    //final so it shouldnt change
    public String generateToken(User user){
        // Determine role name dynamically
        String roleName;
        if (user instanceof Admin admin) {
            roleName = admin.getRole().name();
        } else if (user instanceof Seller seller) {
            roleName = seller.getRole().name();
        } else if (user instanceof Customer customer) {
            roleName = customer.getRole().name();
        } else {
            throw new IllegalStateException("Unknown user type");
        }

        return Jwts.builder()
                .setSubject(user.getFirstname())
                .claim("role", roleName) // single role as string
                .claim("username", user.getEmail())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
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

