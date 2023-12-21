package com.ent.jackpot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Builder
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PlayerActivationModel {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

//    @JsonProperty("email")
//    private String email;
//
//    @JsonProperty("phoneNumber")
//    private String phoneNumber;

}
