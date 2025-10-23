package com.urbancartel.config;

import com.urbancartel.security.JwtFilter;
//JwtFilter → Your custom filter that checks JWT tokens for each request.
//FilterChain → Lets the request pass to the next filter or controller after this filter is done.
import com.urbancartel.service.impl.CustomUserDetailsService;
// → Loads user details from your database for authentication.
import org.springframework.beans.factory.annotation.Autowired;
//@Autowired → Automatically injects Spring-managed beans into your class.
import org.springframework.context.annotation.Bean;
//@Bean → Marks a method as producing a Spring-managed object.
import org.springframework.context.annotation.Configuration;
//@Configuration → Marks the class as a Spring configuration class (used to define beans and security rules).
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
//AuthenticationManager → Spring Security component that performs authentication (checks username/password).
import org.springframework.security.authentication.AuthenticationProvider;
//AuthenticationProvider → Component that actually checks credentials and returns an authenticated user.
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//DaoAuthenticationProvider → A ready-to-use AuthenticationProvider that works with a UserDetailsService and password encoder.
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//AuthenticationConfiguration → Helps get the AuthenticationManager bean from Spring configuration.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//HttpSecurity → Lets you configure HTTP security rules (like which endpoints are secured).
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//AbstractHttpConfigurer → Base class for configuring HTTP security.
import org.springframework.security.config.http.SessionCreationPolicy;
//SessionCreationPolicy → Configures how Spring Security handles HTTP sessions (e.g., stateless for JWT).
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//BCryptPasswordEncoder → Password encoder that uses the BCrypt hashing function.
import org.springframework.security.crypto.password.PasswordEncoder;
//PasswordEncoder → Interface for encoding passwords (e.g., hashing).
import org.springframework.security.web.SecurityFilterChain;
//SecurityFilterChain → Defines the chain of filters and security rules applied to HTTP requests.
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//UsernamePasswordAuthenticationFilter → Default filter that processes username/password authentication requests.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//HttpSecurity → Used to configure web-based security for specific HTTP requests.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//@EnableWebSecurity → Enables Spring Security’s web security support and provides the Spring MVC integration.
import org.springframework.security.web.SecurityFilterChain;
//SecurityFilterChain → Represents a filter chain that is capable of being matched against an HttpServletRequest.

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
//@Bean
//@Bean is used on a method inside a class.
//It tells Spring:
//“Hey Spring, call this method and put its return object into the Spring container. Make it a Spring-managed bean.”


//✅ What happens:
//Spring creates a PasswordEncoder object for you
//You can inject it anywhere using @Autowired
//Spring manages its lifecycle (singleton by default, can be changed)

//2️⃣ @Configuration
//@Configuration is used on a class.
//It tells Spring:
//“This class has methods annotated with @Bean. Treat this class as a source of Spring-managed beans.”
//Basically, @Configuration enables the @Bean methods inside it.
//
//@Bean → “Make this object a Spring-managed bean.”
//@Configuration → “This class is a bean factory.”
//They are related but not exactly the same. @Configuration makes @Bean methods behave properly as singletons.

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register",
                                "/api/seller/**", "/api/admin/categories/**",
                                "/api/admin/sellers/**", "/api/auth/admin/**","/api/auth/createadmin","/api/auth/check","/api/admin/products/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        //Create a CORS configuration object.
        configuration.setAllowedOrigins(List.of("http://localhost:5174/","http://localhost:5173/"));
        /*
        Only allow requests from this frontend URL.
Your React app runs on http://localhost:5173, so that’s allowed.
        */
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        /*
Allow these HTTP methods from the frontend.
Example: your frontend can send GET, POST, PUT, DELETE requests.
         */
        configuration.setAllowedHeaders(List.of("*"));
 //       Allow all headers in requests. Example: Authorization, Content-Type, etc.

        configuration.setAllowCredentials(true);
//Allow sending cookies or authentication info with requests.
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
        //Create a CORS source that applies this configuration.
        //
        //"/**" → Apply to all endpoints in your backend.
        //
        //Return this to Spring, so it knows how to handle cross-origin requests.
    }

}



/*
1. AuthenticationManager

What it is: The boss/manager of authentication.

What it does: Decides which provider to use to check a user’s credentials.

Analogy: Like a security manager at a building entrance who says:
“Hey, check with the ID card scanner, fingerprint scanner, or face recognition—whatever you have configured.”

Key point: You don’t check credentials directly here; it delegates.

2. AuthenticationProvider

What it is: A worker/security guard who actually checks the credentials.

What it does: Receives a username/password and decides if it’s valid.

Analogy: The ID card scanner or fingerprint scanner itself.

Key point: You can have many providers (one for DB, one for LDAP, one for OAuth, etc.).

3. DaoAuthenticationProvider

What it is: A ready-made AuthenticationProvider for database users.

What it does:

Uses a UserDetailsService to load user info from a database.

Uses a PasswordEncoder to verify the password.

Analogy: A pre-built ID card scanner that knows how to check your employee ID in the company database.

Key point: You usually use this if you store users in your own database.
 */




//https://chatgpt.com/share/68d2430a-b9cc-8006-9d0a-40270cdc48b8






