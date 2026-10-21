package com.example.pronostic.services;

import com.example.pronostic.dtos.FootballApiResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FootballApiService {

    private final RestTemplate restTemplate;
    
    @Value("${football.api.key}")
    private String apiKey;
    private final String API_URL = "https://api.football-data.org/v4/competitions/PL/matches";

    public FootballApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FootballApiResponseDto fetchPremierLeagueMatches() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FootballApiResponseDto> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                entity,
                FootballApiResponseDto.class
        );

        return response.getBody();
    }
}