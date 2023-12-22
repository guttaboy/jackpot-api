package com.ent.jackpot.service;

import com.ent.jackpot.entity.Player;
import com.ent.jackpot.entity.Prediction;
import com.ent.jackpot.model.PredictionModel;
import com.ent.jackpot.repository.PredictionRepository;
import com.ent.jackpot.service.common.CommonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PredictionService {

    @Autowired
    PredictionRepository predictionRepository;

    @Autowired
    CommonService commonService;

    public String createMatchPrediction(String playerName, Integer jackpotId, PredictionModel predictionModel){

        //get PlayerId by using userName
        var playerId = commonService.getPlayerIdByPlayerName(playerName);

        //get Match Id by using Match Number
        var matchId = commonService.getMatchIdByMatchNumber(predictionModel.getMatchNumber(), jackpotId);

        var prediction = Prediction.builder()
                .playerId(playerId)
                .matchId(matchId)
                .jackpotId(jackpotId)
                .predictedTeam(predictionModel.getPredictedTeam())
                .build();
        predictionRepository.save(prediction);

        return "Prediction added successfully!";
    }
}
