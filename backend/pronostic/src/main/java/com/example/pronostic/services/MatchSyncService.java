package com.example.pronostic.services;

import com.example.pronostic.dtos.FootballApiResponseDto.ApiMatchDto;
import com.example.pronostic.dtos.FootballApiResponseDto;
import com.example.pronostic.entities.Matches;
import com.example.pronostic.repositories.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class MatchSyncService {

    private final MatchRepository matchRepository;
    private final FootballApiService footballApiService;

    public MatchSyncService(MatchRepository matchRepository, FootballApiService footballApiService) {
        this.matchRepository = matchRepository;
        this.footballApiService = footballApiService;
    }

    @Transactional
    public void syncMatches() {
        FootballApiResponseDto apiResponse = footballApiService.fetchPremierLeagueMatches();

        if (apiResponse == null || apiResponse.getMatches() == null) {
            return;
        }

        for (ApiMatchDto extMatch : apiResponse.getMatches()) {
            Long apiMatchId = extMatch.getId();
            
            // Extraction sécurisée des scores (parfois null si le match n'a pas commencé)
            int homeScore = (extMatch.getScore() != null && extMatch.getScore().getFullTime() != null 
                    && extMatch.getScore().getFullTime().getHome() != null) 
                    ? extMatch.getScore().getFullTime().getHome() : 0;

            int awayScore = (extMatch.getScore() != null && extMatch.getScore().getFullTime() != null 
                    && extMatch.getScore().getFullTime().getAway() != null) 
                    ? extMatch.getScore().getFullTime().getAway() : 0;

            boolean hasStarted = "IN_PLAY".equals(extMatch.getStatus()) || "FINISHED".equals(extMatch.getStatus());
            Instant startTime = extMatch.getUtcDate() != null ? Instant.parse(extMatch.getUtcDate()) : null;

            // 1. Recherche en base
            Optional<Matches> existingMatchOpt = matchRepository.findByApiMatchId(apiMatchId);

            if (existingMatchOpt.isPresent()) {
                // --- MISE À JOUR (UPDATE si changement) ---
                Matches match = existingMatchOpt.get();
                boolean hasChanged = false;

                if (match.getHomeTeamScore() != homeScore) {
                    match.setHomeTeamScore(homeScore);
                    hasChanged = true;
                }
                if (match.getAwayTeamScore() != awayScore) {
                    match.setAwayTeamScore(awayScore);
                    hasChanged = true;
                }
                if (match.isHasStarted() != hasStarted) {
                    match.setHasStarted(hasStarted);
                    hasChanged = true;
                }

                if (hasChanged) {
                    matchRepository.save(match);
                }

            } else {
                // --- CRÉATION (INSERT) ---
                Matches newMatch = new Matches();
                newMatch.setApiMatchId(apiMatchId);
                newMatch.setHomeTeamScore(homeScore);
                newMatch.setAwayTeamScore(awayScore);
                newMatch.setHasStarted(hasStarted);
                newMatch.setStartTime(startTime);
                
                matchRepository.save(newMatch);
            }
        }
    }
}