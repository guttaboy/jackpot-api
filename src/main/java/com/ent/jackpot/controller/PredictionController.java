package com.ent.jackpot.controller;

import com.ent.jackpot.model.PredictionModel;
import com.ent.jackpot.service.PredictionService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/jackpot/v1")
@Slf4j
public class PredictionController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    PredictionService predictionService;

    @PostMapping(value = "/createPrediction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createPrediction(String playerName, Integer jackpotId, @RequestBody PredictionModel predictionModel){
        //validate player name
        validatorUtil.validateUserName(playerName);
        //Validate Jackpot Id
        validatorUtil.validateJackpot(jackpotId);
        //validate predictionModel request body
        validatorUtil.validatePredictionModel(predictionModel);

        return predictionService.createMatchPrediction(playerName, jackpotId, predictionModel);
    }

}
