package com.example.pronostic.repositories;

import com.example.pronostic.entities.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Matches, Long> {
    Optional<Matches> findByApiMatchId(Long apiMatchId);
}