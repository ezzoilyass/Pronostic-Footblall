package com.example.pronostic.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "pronostics", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "match_id"})
})
@Data 
@NoArgsConstructor 
public class Pronostics {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Matches match;
}
