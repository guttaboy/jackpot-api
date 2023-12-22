package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JackpotCreateResponseModel {

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("jackpotId")
    private Integer jackpotId;

    @JsonProperty("statusCode")
    private HttpStatus statusCode;

}
