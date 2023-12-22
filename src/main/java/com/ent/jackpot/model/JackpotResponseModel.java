package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JackpotResponseModel {

    @JsonProperty("jackpotName")
    private String jackpotName;

    @JsonProperty("jackpotAmount")
    private String jackpotAmount;

    @JsonProperty("jackpotCreatedPlayer")
    private String jackpotCreatedPlayer;

    @JsonProperty("playersList")
    private List<String> playersList;

    @JsonProperty("matchesList")
    private List<MatchesResponseModel> matchesList;
}
