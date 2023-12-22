package com.ent.jackpot.controller;

import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.model.CreateResponseModel;
import com.ent.jackpot.model.MatchCreateModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.service.MatchService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/jackpot/v1")
@Slf4j
public class MatchController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    MatchService matchService;

    @Transactional
    @PostMapping(value = "/createMatches", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateResponseModel createMatchesInJackpot(
            @RequestBody List<MatchCreateModel> matchCreateModelList, Integer jackpotId) {
        validatorUtil.validateJackpot(jackpotId);
        var matchNumbersList = new ArrayList<String>();
        var matchNumbersSet = new HashSet<String>();
        matchCreateModelList.forEach(matchCreateModel -> {
            matchNumbersList.add(matchCreateModel.getMatchNumber());
            matchNumbersSet.add(matchCreateModel.getMatchNumber());
            validatorUtil.validateMatchRequestModel(matchCreateModel);
        });
        //validate if match numbers in the list are unique
        if(matchNumbersList.size() != matchNumbersSet.size()){
            throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", "Match Numbers Must Be Unique");
        }
        // service call here
        return matchService.createMatchesList(matchCreateModelList, jackpotId, matchNumbersList);
    }

    @Transactional
    @GetMapping(value = "/getMatches")
    public List<MatchesResponseModel> retrieveMatchesInJackpot(Integer jackpotId) {
        validatorUtil.validateJackpot(jackpotId);
        // service call here
        return matchService.getMatchesFromJackpot(jackpotId);
    }

}
