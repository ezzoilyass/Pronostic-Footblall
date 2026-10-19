package com.example.pronostic.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pronostic.dtos.UserCreationDto;
import com.example.pronostic.entities.Users;
import com.example.pronostic.repositories.UsersReposiory;


@Service
@Transactional 
public class UsersService {
    private final UsersReposiory usersRepository;

    public UsersService(UsersReposiory ur){
        usersRepository = ur;
    }

    public Users createUser(UserCreationDto ucd){
        String firstName = ucd.getFirstName();
        String secondName = ucd.getSecondName();
        String userName = ucd.getUserName();
        String password = ucd.getPassword();
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
        user.setPassword(password);

        return user;
    }

}
