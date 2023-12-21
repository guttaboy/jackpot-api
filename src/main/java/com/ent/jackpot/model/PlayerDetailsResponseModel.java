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
public class PlayerDetailsResponseModel {

    @JsonProperty("playerName")
    private String playerName;

    @JsonProperty("jackpotsList")
    private List<PlayerJackpotResponseModel> jackpotsList;

}
