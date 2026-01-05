package com.churninsight.service;

import com.churninsight.model.User;
import com.churninsight.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("[UserService] Buscando usuario: {}", username);
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                logger.error("[UserService] Usuario NO encontrado: {}", username);
                return new UsernameNotFoundException("Usuario no encontrado: " + username);
            });
        
        logger.debug("[UserService] Usuario encontrado: {}, activo: {}", user.getUsername(), user.isActive());
        
        if (!user.isActive()) {
            logger.warn("[UserService] Usuario inactivo: {}", username);
            throw new UsernameNotFoundException("Usuario inactivo");
        }
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
    
    public User registerUser(String username, String password, String email, String fullName) {
        logger.info("[UserService] Registrando nuevo usuario: {}", username);
        
        if (userRepository.existsByUsername(username)) {
            logger.warn("[UserService] Username ya existe: {}", username);
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        if (userRepository.existsByEmail(email)) {
            logger.warn("[UserService] Email ya existe: {}", email);
            throw new RuntimeException("El email ya está registrado");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole("ANALISTA");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }
    
    public String generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("No se encontró usuario con ese email"));
        
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        
        userRepository.save(user);
        return token;
    }
    
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
            .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));
        
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        
        userRepository.save(user);
    }
}
