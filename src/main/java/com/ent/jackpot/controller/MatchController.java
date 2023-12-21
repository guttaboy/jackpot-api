package com.ent.jackpot.controller;

import com.ent.jackpot.model.MatchRequestModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.service.MatchService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/profile/v1")
@Slf4j
public class MatchController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    MatchService matchService;

    @Transactional
    @PostMapping(value = "/createMatch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createMatchInJackpot(
            @RequestBody MatchRequestModel matchRequestModel, Integer jackpotId) {
        validatorUtil.validateJackpot(jackpotId);
        validatorUtil.validateMatchRequestModel(matchRequestModel);
        // service call here
        return matchService.createMatch(matchRequestModel, jackpotId);
    }

    @Transactional
    @PostMapping(value = "/createMatches", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createMatchesInJackpot(
            @RequestBody List<MatchRequestModel> matchRequestModelList, Integer jackpotId) {
        validatorUtil.validateJackpot(jackpotId);
        matchRequestModelList.forEach(matchRequestModel -> {
            validatorUtil.validateMatchRequestModel(matchRequestModel);
        });
        // service call here
        return matchService.createMatchesList(matchRequestModelList, jackpotId);
    }

    @Transactional
    @GetMapping(value = "/getMatches")
    public List<MatchesResponseModel> retrieveMatchesInJackpot(Integer jackpotId) {
        validatorUtil.validateJackpot(jackpotId);
        // service call here
        return matchService.getMatchesFromJackpot(jackpotId);
    }

}
