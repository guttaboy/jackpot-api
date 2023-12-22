package com.ent.jackpot.service;

import com.ent.jackpot.entity.Jackpot;
import com.ent.jackpot.entity.Match;
import com.ent.jackpot.entity.Player;
import com.ent.jackpot.entity.PlayerAssociation;
import com.ent.jackpot.jpaspecs.JackpotSpecs;
import com.ent.jackpot.jpaspecs.MatchSpecs;
import com.ent.jackpot.jpaspecs.PlayerAssociationSpecs;
import com.ent.jackpot.jpaspecs.PlayerSpecs;
import com.ent.jackpot.model.JackpotActivationModel;
import com.ent.jackpot.model.JackpotCreateResponseModel;
import com.ent.jackpot.model.JackpotResponseModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.repository.JackpotRepository;
import com.ent.jackpot.repository.MatchRepository;
import com.ent.jackpot.repository.PlayerAssociationRepository;
import com.ent.jackpot.repository.PlayerRepository;
import com.ent.jackpot.service.common.CommonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class JackpotService {

    @Autowired
    JackpotRepository jackpotRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerAssociationRepository playerAssociationRepository;

    @Autowired
    JackpotSpecs jackpotSpecs;

    @Autowired
    PlayerSpecs playerSpecs;

    @Autowired
    PlayerAssociationSpecs playerAssociationSpecs;

    @Autowired
    CommonService commonService;

    public JackpotCreateResponseModel createJackpot(String organizerName, JackpotActivationModel jackpotActivationModel){
        //get player id by player name
        var jackpotCreatedPlayerId = commonService.getPlayerIdByPlayerName(organizerName);
        //create Jackpot entity and save it
        var jackpot = Jackpot.builder()
                .jackpotMoney(jackpotActivationModel.getJackpotAmount())
                .jackpotName(jackpotActivationModel.getJackpotGroupName())
                .jackpotCreatedPlayerId(jackpotCreatedPlayerId)
                .build();
        Jackpot savedJackpot = jackpotRepository.save(jackpot);

        //get the player Ids from request payload to create association between players and jackpot
        var userNames = jackpotActivationModel.getPlayersList();
        var playerIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userNames)){
            for(String userName : userNames){
                var playerActId = commonService.getPlayerIdByPlayerName(userName);
                playerIds.add(playerActId);
            }
        }

        //save association details by using playersIds and jackpotId
        if(!CollectionUtils.isEmpty(playerIds)) {
            for (Object playerId : playerIds) {
                var playerAssociation = PlayerAssociation.builder()
                        .playerId((Integer) playerId)
                        .jackpotId(savedJackpot.getJackpotId())
                        .build();
                playerAssociationRepository.save(playerAssociation);
            }
        }
        var jackpotCreateResponseModel = new JackpotCreateResponseModel();
        jackpotCreateResponseModel.setResponseMessage("Jackpot Created Successfully!");
        jackpotCreateResponseModel.setStatusCode(HttpStatus.CREATED);
        jackpotCreateResponseModel.setJackpotId(savedJackpot.getJackpotId());
        return jackpotCreateResponseModel;
    }

    public JackpotResponseModel retrieveJackpotDetails(Integer jackpotId){
        //retrieve jackpot details using jackpotId
        var jackpotResponseModel = commonService.getJackpotDetails(jackpotId);

        //retrieve Matches details using jackpotId
        var matchResponseBodyList = new ArrayList<MatchesResponseModel>();
        commonService.getMatchDetailsByJackpotId(jackpotId, matchResponseBodyList);

        jackpotResponseModel.setMatchesList(matchResponseBodyList);
        return jackpotResponseModel;
    }
}
