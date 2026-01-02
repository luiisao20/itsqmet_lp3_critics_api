package com.itsqmet.service;

import com.itsqmet.entity.User;
import com.itsqmet.repository.UserRepository;
import com.itsqmet.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById (String uuid) {
        return userRepository.findById(uuid);
    }

    public Optional<User> getUserByUsername (String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(String uuid, User user) {
        User oldUser = getUserById(uuid).orElseThrow(() -> new RuntimeException("User not found!"));
        oldUser.setUsername(user.getUsername());
        oldUser.setLastName(user.getLastName());
        oldUser.setUsername(user.getUsername());
        oldUser.setRole(user.getRole());

        if (user.getUuid() != null && user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(oldUser);
    }

    public User registerUser(User user) {
        // Encrypt password
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        // Assign student role to all users
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public void deleteUser(String uuid) {
        User user = getUserById(uuid).orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find in db the user, if it doesn't exist throw error
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        // Use builder to create the object that spring security gets as an authenticated user
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
