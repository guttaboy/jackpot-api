package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Builder
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class JackpotActivationModel {

    @JsonProperty("jackpotGroupName")
    private String jackpotGroupName;

    @JsonProperty("jackpotAmount")
    private String jackpotAmount;

    @JsonProperty("playersList")
    private List<String> playersList;

}
