package com.ent.jackpot.controller;

import com.ent.jackpot.model.MatchResultResponseModel;
import com.ent.jackpot.service.ResultsService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/jackpot/v1")
@Slf4j
public class ResultsController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    ResultsService resultsService;

    @GetMapping(value="/getResults")
    public MatchResultResponseModel getMatchResults(Integer jackpotId, String matchNumber, String playerName){

        //validate request payload
        validatorUtil.validateJackpot(jackpotId);
        //validate Player
        validatorUtil.validateUserName(playerName);
        //validate Match
        validatorUtil.validateMatchNumber(jackpotId, matchNumber, playerName);
        return resultsService.getMatchResult(jackpotId, matchNumber, playerName);
    }
}
