package com.example.pronostic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pronostic.dtos.UserCreationDto;
import com.example.pronostic.entities.Users;
import com.example.pronostic.services.UsersService;

@RestController 
@RequestMapping ("/api/users")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService us){
        usersService = us;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreationDto ucd){
        try{
            Users user = usersService.createUser(ucd);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }
}
