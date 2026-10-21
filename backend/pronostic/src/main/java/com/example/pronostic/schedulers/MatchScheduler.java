package com.example.pronostic.schedulers;

import com.example.pronostic.services.MatchSyncService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MatchScheduler {

    private final MatchSyncService matchSyncService;

    public MatchScheduler(MatchSyncService matchSyncService) {
        this.matchSyncService = matchSyncService;
    }

    // S'exécute toutes les 2 minutes (120 000 millisecondes)
    @Scheduled(fixedRate = 120000)
    public void triggerSync() {
        try {
            matchSyncService.syncMatches();
        } catch (Exception e) {
            System.err.println("Erreur synchro matchs : " + e.getMessage());
        }
    }
}