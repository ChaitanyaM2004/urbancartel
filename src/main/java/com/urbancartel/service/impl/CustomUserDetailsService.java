//package com.urbancartel.service.impl;
//
//import com.urbancartel.entity.Admin;
//import com.urbancartel.entity.User;
//import com.urbancartel.repository.AdminRepository;
//import com.urbancartel.repository.UserRepository;
//import com.urbancartel.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import com.urbancartel.entity.Roles; // 💡 Import your Roles enum
//
//import java.util.Collection;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    // ... (UserRepository and loadUserByUsername method start
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private AdminRepository adminRepository;
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(()->new UsernameNotFoundException("user not found with username: "+email));
//
//
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                authorities
//        );
//    }
//}

package com.urbancartel.service.impl;

import com.urbancartel.entity.Admin;
import com.urbancartel.entity.Customer;
import com.urbancartel.entity.Seller;
import com.urbancartel.entity.User;
import com.urbancartel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Should handle all users

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Determine role based on subclass
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

        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_" + roleName)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
