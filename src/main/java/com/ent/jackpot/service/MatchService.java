package com.ent.jackpot.service;

import com.ent.jackpot.entity.Match;
import com.ent.jackpot.model.MatchRequestModel;
import com.ent.jackpot.repository.MatchRepository;
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
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    public String createMatch(MatchRequestModel matchRequestModel, Integer jackpotId){

        var match = Match.builder()
                .matchNumber(matchRequestModel.getMatchNumber())
                .matchName(matchRequestModel.getMatchName())
                .team1(matchRequestModel.getTeam1())
                .team2(matchRequestModel.getTeam2())
                .jackpotId(jackpotId)
                .build();

        matchRepository.save(match);

        return "Match Created Successfully!";
    }
}
