package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    @Column(length=50)
    @ManyToMany(fetch=FetchType.EAGER)//fetch eager is used here to load roles fastly when needed for security purposes
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;
}
