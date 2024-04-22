package com.nisum.apirest.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.nisum.apirest.dto.LoginDto;
import com.nisum.apirest.dto.UserDto;
import com.nisum.apirest.exception.BusinessException;
import com.nisum.apirest.exception.UserNotFoundException;
import com.nisum.apirest.model.User;
import com.nisum.apirest.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDto login(LoginDto loginDto) {
        log.info("Datos del usuario: {}", loginDto.getEmail());
        try {
            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(loginDto.getEmail()));

            if (!user.getIsActive()) {
                throw new BusinessException("El usuario no está activo");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            String jwt = jwtService.generateToken(user);
            user.setLastLogin(LocalDateTime.now());
            user.setToken(jwt);
            user = userRepository.save(user);
            return UserDto.fromEntity(user);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
