package com.ent.jackpot.service;

import com.ent.jackpot.entity.Match;
import com.ent.jackpot.entity.Points;
import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.model.CreateMatchResponseModel;
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

    public CreateMatchResponseModel createMatchesList(List<MatchCreateModel> matchCreateModelList, Integer jackpotId){
        matchCreateModelList.forEach(matchCreateModel -> {
            createMatch(matchCreateModel, jackpotId);
        });
        var createMatchResponseModel = new CreateMatchResponseModel();
        createMatchResponseModel.setMessage("Matches created successfully");
        createMatchResponseModel.setStatusCode(HttpStatus.CREATED);

        return createMatchResponseModel;
    }

    public void createMatch(MatchCreateModel matchCreateModel, Integer jackpotId){

        var match = Match.builder()
                .matchNumber(matchCreateModel.getMatchNumber())
                .matchName(matchCreateModel.getMatchName())
                .team1(matchCreateModel.getTeam1())
                .team2(matchCreateModel.getTeam2())
                .jackpotId(jackpotId)
                .build();

        var createdMatch = matchRepository.save(match);
        if(createdMatch == null) {
            throw new JackpotApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Data Base Issue", "Unexpected exception occured while creating the records");
        }

        //set Maximum points to the match while creating
        var points = Points.builder()
                .matchId(createdMatch.getMatchId())
                .maximumPoints(matchCreateModel.getMaxPoints())
                .build();
        var createdPoints = pointsRepository.save(points);
        if(createdPoints == null) {
            throw new JackpotApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Data Base Issue", "Unexpected exception occured while creating the records");
        }

    }

    public List<MatchesResponseModel> getMatchesFromJackpot(Integer jackpotId){

        //retrieve Matches details using jackpotId
        var matchResponseBodyList = new ArrayList<MatchesResponseModel>();
        commonService.getMatchDetailsByJackpotId(jackpotId, matchResponseBodyList);

        return matchResponseBodyList;

    }
}
