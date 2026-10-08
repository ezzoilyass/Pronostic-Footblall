package com.example.pronostic.entities;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@NoArgsConstructor
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Teams homeTeam;
    private int homeTeamScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Teams awayTeam;
    private int awayTeamScore;

    private Instant startTime;


    private boolean hasStarted = false;

}
