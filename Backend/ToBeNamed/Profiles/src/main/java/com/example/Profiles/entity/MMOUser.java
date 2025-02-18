package com.example.Profiles.entity;

import com.example.Profiles.repository.MMOUserRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class MMOUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String username;
    @Column(nullable = false, unique = true, length = 124)
    private String email;
    @Column(nullable = false)
    private String password;
    private String role = "USER";

    private Integer energy = 100;

    private Integer healthPoints = 150;

    private Integer maxHealthPoints = 150;


    public MMOUser() {
        this.role = "USER";
        this.energy = 100;
        this.healthPoints = 100;
        this.maxHealthPoints = 150;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
