package com.ent.jackpot.controller;

import com.ent.jackpot.model.PlayerActivationModel;
import com.ent.jackpot.model.PlayerDetailsResponseModel;
import com.ent.jackpot.service.PlayerActivationService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/profile/v1")
@Slf4j
public class PlayerActivationController {

    @Autowired ValidatorUtil validatorUtil;

    @Autowired
    PlayerActivationService playerActivationService;

    @PostMapping(value = "/createPlayer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createPlayer(@RequestBody PlayerActivationModel playerActivationModel) {
        //payload validation
        validatorUtil.validatePlayerActivationModel(playerActivationModel);
        return playerActivationService.createPlayerActivation(playerActivationModel);
    }

    @GetMapping(value="/getPlayerDetails")
    public PlayerDetailsResponseModel getPlayerDetails(String userName, String key) {
        //payload validation
        validatorUtil.validateUserName(userName);
        validatorUtil.validateKey(key);
        return playerActivationService.getPlayerActiveDetails(userName, key);
    }

}
