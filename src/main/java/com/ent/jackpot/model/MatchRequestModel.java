package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Builder
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class MatchRequestModel {

    @JsonProperty("matchNumber")
    private String matchNumber;

    @JsonProperty("matchName")
    private String matchName;

    @JsonProperty("team1")
    private String team1;

    @JsonProperty("team2")
    private String team2;

}
