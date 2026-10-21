package com.example.pronostic.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballApiResponseDto {

    private List<ApiMatchDto> matches;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiMatchDto {
        private Long id;
        private String utcDate;
        private String status;
        private ApiTeamDto homeTeam;
        private ApiTeamDto awayTeam;
        private ApiScoreDto score;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiTeamDto {
        private Long id;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiScoreDto {
        private ApiScoreDetailsDto fullTime;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiScoreDetailsDto {
        private Integer home;
        private Integer away;
    }
}