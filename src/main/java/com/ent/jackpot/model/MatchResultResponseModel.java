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
public class MatchResultResponseModel {

    @JsonProperty("jackpotId")
    private Integer jackpotId;

    @JsonProperty("matchNumber")
    private String matchNumber;

    @JsonProperty("result")
    private String result;

    @JsonProperty("winTeam")
    private String winTeam;

    @JsonProperty("pointsGained")
    private String pointsGained;

    @JsonProperty("maxPoints")
    private String maxPoints;

    @JsonProperty("comments")
    private String comments;
}
