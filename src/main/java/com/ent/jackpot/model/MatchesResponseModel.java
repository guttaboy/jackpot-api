package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchesResponseModel {

    @JsonProperty("matchNumber")
    private String matchNumber;

    @JsonProperty("matchName")
    private String matchName;

    @JsonProperty("team1")
    private String team1;

    @JsonProperty("team2")
    private String team2;

    @JsonProperty("matchDate")
    private String matchDate;

//    @JsonProperty("prediction")
//    private String prediction;

    @JsonProperty("winner")
    private String winner;

    @JsonProperty("maximumPoints")
    private Integer maximumPoints;

}
