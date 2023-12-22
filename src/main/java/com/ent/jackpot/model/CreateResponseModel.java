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
public class CreateResponseModel {

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("statusCode")
    private HttpStatus statusCode;
}
