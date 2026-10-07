package com.example.pronostic.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    protected String firstName;
    protected String secondName;
    protected String userName;
    protected String password;

    public Users(String firstName, String secondName, String userName, String password){
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.userName = userName;
    }
}
