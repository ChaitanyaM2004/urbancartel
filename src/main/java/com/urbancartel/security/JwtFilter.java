package com.urbancartel.security;
import jakarta.servlet.FilterChain;
//FilterChain → Lets the request pass to the next filter or controller after this filter is done.
import jakarta.servlet.ServletException;
//ServletException → Exception thrown if something goes wrong in the filter or servlet.
import jakarta.servlet.http.HttpServletRequest;
//HttpServletRequest → Represents the incoming HTTP request (headers, params, etc.).
import jakarta.servlet.http.HttpServletResponse;
//HttpServletResponse → Represents the response you will send back to the client.
import org.springframework.beans.factory.annotation.Autowired;
//@Autowired → Automatically injects dependencies (like services or utils) into this class.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//UsernamePasswordAuthenticationToken → Stores authenticated user details for Spring Security.
import org.springframework.security.core.context.SecurityContextHolder;
//SecurityContextHolder → Holds the current user’s authentication info for this request.
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//WebAuthenticationDetailsSource → Helps attach extra request details (like IP, session) to the authentication object.
import org.springframework.stereotype.Component;
//OncePerRequestFilter → Base class for a filter that runs only once per request.
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


//how does a browser header looks like
//        GET /api/user/profile HTTP/1.1	The request line (method + path + HTTP version)
//        Host: example.com	Domain of the server
//        Authorization: Bearer <JWT>	Your JWT token to authenticate the request
//        Content-Type: application/json	Type of data you are sending
//        User-Agent: ...	Info about your browser
//        Accept: application/json	What kind of response you can accept

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI(); // gets URL like /api/auth/admin/create

        // ✅ Skip JWT validation for public endpoints
        if (request.getServletPath().startsWith("/api/auth")) {
            chain.doFilter(request, response);
            System.out.println("Skipping JWT for: " + path);
            return; // skip the rest of the JWT logic
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("after skipping path");
            String token = authHeader.substring(7);
            try {
                String email = jwtUtil.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(token, email)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(email, null, null);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        System.out.println("Token validated successfully for user: " + email);
                    } else {
                        System.out.println("Token validation failed for user: " + email);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing JWT token: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }

}