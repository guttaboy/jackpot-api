package com.ent.jackpot.service;

import com.ent.jackpot.entity.Match;
import com.ent.jackpot.entity.Player;
import com.ent.jackpot.entity.PlayerAssociation;
import com.ent.jackpot.jpaspecs.MatchSpecs;
import com.ent.jackpot.jpaspecs.PlayerAssociationSpecs;
import com.ent.jackpot.model.*;
import com.ent.jackpot.repository.MatchRepository;
import com.ent.jackpot.repository.PlayerAssociationRepository;
import com.ent.jackpot.repository.PlayerRepository;
import com.ent.jackpot.service.common.CommonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PlayerActivationService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerAssociationRepository playerAssociationRepository;

    @Autowired
    PlayerAssociationSpecs playerAssociationSpecs;

    @Autowired
    CommonService commonService;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchSpecs matchSpecs;

    public String createPlayerActivation(PlayerActivationModel playerActivationModel){

        var player = Player.builder()
                .playerFirstName(playerActivationModel.getFirstName())
                .playerLastName(playerActivationModel.getLastName())
                .password(playerActivationModel.getPassword())
                .userName(playerActivationModel.getUserName())
                .build();
        var savedJackpot = playerRepository.save(player);
        return "Player Created!";
    }

    public PlayerDetailsResponseModel getPlayerActiveDetails(String userName, String key){
        var responseBody = new PlayerDetailsResponseModel();
        responseBody.setPlayerName(userName);

        //TODO: validate the user and password first

        //once the validation is done get details
        //get PlayerId by using userName

        var playerId = commonService.getPlayerIdByPlayerName(userName);
        var jackpotIds = new ArrayList<Integer>();
        //fetch jackpot details the player is associated to
        List<PlayerAssociation> playerAssoociationEntityList = playerAssociationRepository.findAll(playerAssociationSpecs.getPlayerAssociationByPlayerId(playerId));

        if(!CollectionUtils.isEmpty(playerAssoociationEntityList)){
            for(PlayerAssociation playerAscnEntity: playerAssoociationEntityList){
                    jackpotIds.add(playerAscnEntity.getJackpotId());
            }
        }

        //retrieve Jackpot details associated to the player
        var playerJackpotResponseModelList = new ArrayList<PlayerJackpotResponseModel>();
        commonService.getJackpotsList(jackpotIds, playerJackpotResponseModelList);

        //retrieve Player Details associated to the jackpot
        retrievePlayersList(jackpotIds, playerJackpotResponseModelList);

        //retrieve Match Details associated to the jackpot
        retrievePlayerMatchDetails(jackpotIds, playerJackpotResponseModelList, playerId);
        responseBody.setJackpotsList(playerJackpotResponseModelList);

        return responseBody;
    }

    private void retrievePlayersList(List<Integer> jackpotIds, List<PlayerJackpotResponseModel> playerJackpotResponseModelList){
        //retrieve Match Details associated to the jackpot
        jackpotIds.forEach(jackpot -> {
            var playerIds = new ArrayList<Integer>();
            var playerNames = new ArrayList<String>();
            commonService.getPlayerIdsFromPlrAscnByJkptId(jackpot, playerIds);
            commonService.getPlayerNameByPlayerIds(playerIds, playerNames);
            playerJackpotResponseModelList.stream()
                    .filter(jackpotResponse -> jackpotResponse.getJackpotId().equals(String.valueOf(jackpot)))
                    //Assign playnames to the associated jackpot
                    .forEach(jackpotResponse -> jackpotResponse.setPlayersList(playerNames));
        });
    }

    private void retrievePlayerMatchDetails(List<Integer> jackpotIds, List<PlayerJackpotResponseModel> playerJackpotResponseModelList, Integer playerId){
        //retrieve Match Details associated to the jackpot
        jackpotIds.forEach(jackpot -> {
            List<Match> matchEntityList = matchRepository.findAll(matchSpecs.getMatchDetailsByJackpotId(jackpot));
            if(!CollectionUtils.isEmpty(matchEntityList)) {
                var playerMatchResponseBodyList = new ArrayList<PlayerMatchResponseModel>();
                for (Match matchEntity : matchEntityList) {
                    var match = new PlayerMatchResponseModel();
                    match.setMatchName(matchEntity.getMatchName());
                    match.setMatchNumber(matchEntity.getMatchNumber());
                    match.setTeam1(matchEntity.getTeam1());
                    match.setTeam2(matchEntity.getTeam2());
                    //retrieve Prediction Details
//                    var predictedTeam = commonService.getPredictionDetails(matchEntity.getMatchId());
                    var predictedTeam = commonService.getPredictionDetailsByMatchIdAndPlayerId(matchEntity.getMatchId(), playerId);
                    match.setPrediction(predictedTeam);
                    //retrieve match points that player gained
                    var pointsEntity = commonService.getMatchPointsByPlayerIdAndMatchId(playerId, matchEntity.getMatchId());
                    if(Objects.nonNull(pointsEntity)){
                        if(pointsEntity.get().getPoints() != null){
                            match.setMatchPoints(pointsEntity.get().getPoints());
                            if(pointsEntity.get().getPoints().equals("1")){
                                match.setResult("Congrats you won the prediction");
                            }else{
                                match.setResult("You lost the prediction");
                            }
                        }else{
                            match.setMatchPoints("N.A");
                            match.setResult("No result yet");
                        }
                    }
                    playerMatchResponseBodyList.add(match);
                }
                // Filter jackpotResponseList based on jackpotID
                playerJackpotResponseModelList.stream()
                    .filter(jackpotResponse -> jackpotResponse.getJackpotId().equals(String.valueOf(jackpot)))
                    // Assign playerMatchResponseBodyList to the filtered list
                    .forEach(jackpotResponse -> jackpotResponse.setMatchesList(playerMatchResponseBodyList));
            }
        });
    }

}
