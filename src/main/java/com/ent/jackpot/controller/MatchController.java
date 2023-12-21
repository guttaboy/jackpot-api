package com.ent.jackpot.controller;

import com.ent.jackpot.model.JackpotActivationModel;
import com.ent.jackpot.model.MatchRequestModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.service.MatchService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/profile/v1")
@Slf4j
public class MatchController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    MatchService matchService;

    @PostMapping(value = "/createMatch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createMatchInJackpot(
            @RequestBody MatchRequestModel matchRequestModel, Integer jackpotId) {
        validatorUtil.validateJackpot(jackpotId);
        validatorUtil.validateMatchRequestModel(matchRequestModel);
        // service call here
        return matchService.createMatch(matchRequestModel, jackpotId);
    }

}
