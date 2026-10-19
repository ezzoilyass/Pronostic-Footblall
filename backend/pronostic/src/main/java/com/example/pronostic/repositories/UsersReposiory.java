package com.example.pronostic.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pronostic.entities.Users;

@Repository 
public interface UsersReposiory extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String userName);
    Optional<Users> findByFirstNameAndSecondName(String firstName, String secondName);
}
