package com.ent.jackpot.service;

import com.ent.jackpot.entity.Match;
import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.model.MatchRequestModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.repository.MatchRepository;
import com.ent.jackpot.service.common.CommonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    CommonService commonService;

    public String createMatch(MatchRequestModel matchRequestModel, Integer jackpotId){

        var match = Match.builder()
                .matchNumber(matchRequestModel.getMatchNumber())
                .matchName(matchRequestModel.getMatchName())
                .team1(matchRequestModel.getTeam1())
                .team2(matchRequestModel.getTeam2())
                .jackpotId(jackpotId)
                .build();

        var createdMatch = matchRepository.save(match);
        if(createdMatch == null) {
            throw new JackpotApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Data Base Issue", "Unexpected exception occured while creating the records");
        }
        return "Match Created Successfully!";
    }

    public String createMatchesList(List<MatchRequestModel> matchRequestModelList, Integer jackpotId){
        matchRequestModelList.forEach(matchRequestModel -> {
            createMatch(matchRequestModel, jackpotId);
        });
        return "Matches List Created Successfully!";
    }

    public List<MatchesResponseModel> getMatchesFromJackpot(Integer jackpotId){

        //retrieve Matches details using jackpotId
        var matchResponseBodyList = new ArrayList<MatchesResponseModel>();
        commonService.getMatchDetailsByJackpotId(jackpotId, matchResponseBodyList);

        return matchResponseBodyList;

    }
}
