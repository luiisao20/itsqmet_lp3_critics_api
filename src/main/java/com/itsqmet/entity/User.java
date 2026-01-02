package com.itsqmet.entity;

import com.itsqmet.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 5)
    private String lastName;

    @NotBlank(message = "Necesitas un username")
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre de usuario solo puede poseer números y letras")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
