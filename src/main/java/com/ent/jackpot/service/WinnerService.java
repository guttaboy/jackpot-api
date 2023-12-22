package com.ent.jackpot.service;

import com.ent.jackpot.entity.Points;
import com.ent.jackpot.model.CreateResponseModel;
import com.ent.jackpot.model.WinnerCreateModel;
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

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class WinnerService {

    @Autowired
    CommonService commonService;

    @Autowired
    PointsRepository pointsRepository;

    public CreateResponseModel updateWinnerDetails(Integer jackpotId,
                                                   String matchNumber,
                                                   WinnerCreateModel winnerCreateModel){
        //get playerIds from player association table by jackpotId
        var playerIds = new ArrayList<Integer>();
        commonService.getPlayerIdsFromPlrAscnByJkptId(jackpotId, playerIds);

        //get matchId by using matchNumber and jackpotId
        Integer matchId = commonService.getMatchIdByMatchNumber(matchNumber, jackpotId);

        //update points for each player in the jackpot based on the prediction added by player
        playerIds.forEach(playerId -> {
            var predictedTeam = commonService.getPredictionDetailsByMatchIdAndPlayerId(matchId, playerId);
            var pointsEntity = commonService.getMatchPointsByPlayerIdAndMatchId(playerId, matchId);
            if(pointsEntity != null){
                if(winnerCreateModel.getMatchWinner().equals(predictedTeam)){
                    pointsEntity.get().setPoints(String.valueOf(pointsEntity.get().getMaximumPoints()));
                }else{
                    pointsEntity.get().setPoints("0");
                }
                pointsRepository.save(pointsEntity.get());
            }
        });

        var responseModel = new CreateResponseModel();
        responseModel.setResponseMessage("Winner Updated for Match Number: " + matchNumber + " & Jackpot Id: " + jackpotId);
        responseModel.setStatusCode(HttpStatus.CREATED);
        return responseModel;
    }

}
