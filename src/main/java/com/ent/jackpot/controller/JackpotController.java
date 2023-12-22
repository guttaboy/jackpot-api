package com.ent.jackpot.controller;

import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.model.JackpotActivationModel;
import com.ent.jackpot.model.JackpotCreateResponseModel;
import com.ent.jackpot.model.JackpotResponseModel;
import com.ent.jackpot.service.JackpotService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/jackpot/v1")
@Slf4j
public class JackpotController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    JackpotService jackpotActivationService;

    @PostMapping(value = "/createJackpot", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JackpotCreateResponseModel updateJackpotActivationDetails(
            String playerName,
            @RequestBody JackpotActivationModel jackpotActivationModel) throws Exception {
        validatorUtil.validateJackpotActivationModel(jackpotActivationModel);
        // service call here
        var jackpotCreateResponseModel = jackpotActivationService.createJackpot(playerName, jackpotActivationModel);
        return jackpotCreateResponseModel;
    }

    @GetMapping(value="/getJackpot")
    public JackpotResponseModel getJackpotDetails(Integer jackpotId){
        if(jackpotId == null)
            throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", "Provide Jackpot Id to get details");
        //check if entered jackpot is valid
        validatorUtil.validateJackpot(jackpotId);
        //return jackpot details
        return jackpotActivationService.retrieveJackpotDetails(jackpotId);
    }

}
