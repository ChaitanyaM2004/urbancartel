package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length = 50)
    private String firstname;
    @Column(nullable = false,length = 50)
    private String lastname;
    @Column(nullable = false, unique = true,length = 100)
    private String email;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(nullable=false, length = 20, unique = true)
    private String phoneNumber;


//    @ElementCollection(fetch=FetchType.EAGER)
//    @CollectionTable(
//            name="user_roles", // The name of the new join table (can be the same)
//            joinColumns = @JoinColumn(name="user_id")
//    )
//    @Column(name="role_name", nullable = false, length = 50)
//    @Enumerated(EnumType.STRING) // 👈 Tells JPA to persist the ENUM's name (e.g., "SUPER_ADMIN") as a String
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Set<Roles> roles; // 👈 The type is now the ENUM 'Roles'
}
