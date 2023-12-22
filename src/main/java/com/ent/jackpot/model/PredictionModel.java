package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Builder
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PredictionModel {

    @JsonProperty("matchNumber")
    private String matchNumber;

    @JsonProperty("predictedTeam")
    private String predictedTeam;

//    @JsonProperty("matchName")
//    private String matchName;

}
