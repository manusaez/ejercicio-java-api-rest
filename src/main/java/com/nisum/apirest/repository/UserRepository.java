package com.nisum.apirest.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nisum.apirest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByEmail(String mail);
}
