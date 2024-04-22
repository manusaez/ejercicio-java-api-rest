package com.nisum.apirest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nisum.apirest.dto.UserDto;
import com.nisum.apirest.exception.BusinessException;
import com.nisum.apirest.exception.UserNotFoundException;
import com.nisum.apirest.model.Phone;
import com.nisum.apirest.model.User;
import com.nisum.apirest.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto crear(UserDto usuario) {

        if(userRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new BusinessException("Correo ya registrado");
        }

        User user = User.fromDto(usuario);
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(usuario.getPassword()));
        LocalDateTime fechaActual = LocalDateTime.now();
        user.setCreated(fechaActual);
        user.setLastLogin(fechaActual);
        return UserDto.fromEntity(userRepository.save(user));
    }

    public UserDto update(String id, UserDto userDto) {

        UUID userUUID = UUID.fromString(id);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(id));

        if (userDto.getEmail().equals(user.getEmail())) {
            user.setName(userDto.getName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setPhones(userDto.getPhones().stream().map(Phone::fromDto).collect(Collectors.toList()));
            user.setModified(LocalDateTime.now());
            user.setIsActive(userDto.getIsActive());
            
        } else if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new BusinessException("Correo ya registrado");
            
        } 
        return UserDto.fromEntity(userRepository.save(user));

    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).toList();
    }

    public void deleteUserById(String id) {
        UUID userUUID = UUID.fromString(id);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(id));
        if (!user.getIsActive()) {
            throw new BusinessException("El usuario no está activo");
        }
        user.setIsActive(false);
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
    }

    public UserDto getUserById(String id) {
        UUID userUUID = UUID.fromString(id);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(id));
        return UserDto.fromEntity(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return UserDto.fromEntity(user);
    }

}
