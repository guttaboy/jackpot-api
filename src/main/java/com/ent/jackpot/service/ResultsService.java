package com.ent.jackpot.service;

import com.ent.jackpot.model.MatchResultResponseModel;
import com.ent.jackpot.service.common.CommonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ResultsService {

    @Autowired
    CommonService commonService;

    public MatchResultResponseModel getMatchResult(Integer jackpotId, String matchNumber, String playerName){
        var matchResultResponseModel = new MatchResultResponseModel();
        matchResultResponseModel.setJackpotId(jackpotId);
        matchResultResponseModel.setMatchNumber(matchNumber);

        //get matchId by match Number and jackpotId
        var matchId = commonService.getMatchIdByMatchNumber(matchNumber, jackpotId);
        //get player id by player name
        var playerId = commonService.getPlayerIdByPlayerName(playerName);

        return matchResultResponseModel;
    }
}
