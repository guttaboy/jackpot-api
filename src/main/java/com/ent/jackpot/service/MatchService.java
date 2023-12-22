package com.ent.jackpot.service;

import com.ent.jackpot.entity.Match;
import com.ent.jackpot.entity.Points;
import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.model.CreateResponseModel;
import com.ent.jackpot.model.MatchCreateModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.repository.MatchRepository;
import com.ent.jackpot.repository.PointsRepository;
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

    @Autowired
    PointsRepository pointsRepository;

    public CreateResponseModel createMatchesList(List<MatchCreateModel> matchCreateModelList, Integer jackpotId, List<String> requestPayloadMatchNumbersList){
        //fetch existing matches assigned to the given jackpotId
        var matchNumbers = new ArrayList<String>();
        commonService.getMatchNumbersByJackpotId(jackpotId, matchNumbers);
        //validate if request payload matchNumbers exist in DB, if yes throw an exception
        for(String payloadMatchNumber: requestPayloadMatchNumbersList){
            if(matchNumbers.contains(payloadMatchNumber)){
                throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", "Provided Match Number: " + payloadMatchNumber + " Already Exists In The DB");
            }
        }
        matchCreateModelList.forEach(matchCreateModel -> {
            createMatch(matchCreateModel, jackpotId);
        });
        var createMatchResponseModel = new CreateResponseModel();
        createMatchResponseModel.setResponseMessage("Matches created successfully");
        createMatchResponseModel.setStatusCode(HttpStatus.CREATED);

        return createMatchResponseModel;
    }

    public void createMatch(MatchCreateModel matchCreateModel, Integer jackpotId){
        //get player Ids associated the jackpot
        var playerIds = new ArrayList<Integer>();
        commonService.getPlayerIdsFromPlrAscnByJkptId(jackpotId, playerIds);

        var match = Match.builder()
                .matchNumber(matchCreateModel.getMatchNumber())
                .matchName(matchCreateModel.getMatchName())
                .team1(matchCreateModel.getTeam1())
                .team2(matchCreateModel.getTeam2())
                .jackpotId(jackpotId)
                .build();

        var createdMatch = matchRepository.save(match);
        if(createdMatch == null) {
            throw new JackpotApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Data Base Issue", "Unexpected Exception Occurred While Creating The Records");
        }

        //set Maximum points to the match for all players in the jackpot while creating
        playerIds.forEach(playerId -> {
            var points = Points.builder()
                    .matchId(createdMatch.getMatchId())
                    .maximumPoints(matchCreateModel.getMaxPoints())
                    .playerActivationId(playerId)
                    .build();
            var createdPoints = pointsRepository.save(points);
            if(createdPoints == null) {
                throw new JackpotApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Data Base Issue", "Unexpected Exception Occurred While Creating The Records");
            }
        });
    }

    public List<MatchesResponseModel> getMatchesFromJackpot(Integer jackpotId){

        //retrieve Matches details using jackpotId
        var matchResponseBodyList = new ArrayList<MatchesResponseModel>();
        commonService.getMatchDetailsByJackpotId(jackpotId, matchResponseBodyList);

        return matchResponseBodyList;

    }
}
