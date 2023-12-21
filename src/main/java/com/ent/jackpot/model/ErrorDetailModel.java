package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
public class ErrorDetailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("title")
    private String title;

    @JsonProperty("detail")
    private String detail;
}
