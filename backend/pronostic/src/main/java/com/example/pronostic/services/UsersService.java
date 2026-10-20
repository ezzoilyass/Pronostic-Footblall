package com.example.pronostic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pronostic.dtos.UserCreationDto;
import com.example.pronostic.entities.Users;
import com.example.pronostic.repositories.UsersReposiory;


@Service
@Transactional 
public class UsersService {
    private final UsersReposiory usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersReposiory ur, PasswordEncoder pe){
        usersRepository = ur;
        passwordEncoder = pe;
    }

    public Users createUser(UserCreationDto ucd){
        String firstName = ucd.getFirstName();
        String secondName = ucd.getSecondName();
        String userName = ucd.getUserName();
        Optional<Users> existingUser =  usersRepository.findByUserName(userName);
        if(existingUser.isPresent()){
            throw new RuntimeException("Ce nom d'utilisateur est déjà utilisé : " + ucd.getUserName());
        }
        Optional<Users> existingUser2 = usersRepository.findByFirstNameAndSecondName(firstName, secondName);
        if(existingUser2.isPresent()){
            throw new RuntimeException("Ce nom et prenim sont déja utilisé "+ ucd.getFirstName() + " "+ ucd.getSecondName());
        }
        Users user = new Users();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setUserName(userName);
        String hashedPassword = passwordEncoder.encode(ucd.getPassword());
        user.setPassword(hashedPassword);

        return usersRepository.save(user);
    }

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

}
