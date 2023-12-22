package com.ent.jackpot.service.common;

import com.ent.jackpot.entity.*;
import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.jpaspecs.*;
import com.ent.jackpot.model.JackpotResponseModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.model.PlayerJackpotResponseModel;
import com.ent.jackpot.repository.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CommonService {


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
    MatchRepository matchRepository;

    @Autowired
    MatchSpecs matchSpecs;

    @Autowired
    PredictionRepository predictionRepository;

    @Autowired
    PredictionSpecs predictionSpecs;

    @Autowired
    PointsRepository pointsRepository;

    @Autowired
    PointsSpecs pointsSpecs;

    public JackpotResponseModel getJackpotDetails(Integer jackpotId){
        //retrieve jackpot details using jackpotId
        var jackpotResponseModel = new JackpotResponseModel();
        var paramMap = getJackpot(jackpotId);
        jackpotResponseModel.setJackpotName(String.valueOf(paramMap.get("JACKPOT_NAME")));
        jackpotResponseModel.setJackpotAmount(String.valueOf(paramMap.get("JACKPOT_AMOUNT")));
        jackpotResponseModel.setJackpotCreatedPlayer(getPlayerNameByPlayerId(paramMap.get("JACKPOT_CRTD_PLR")));

        //Extract players from PlayerAssociation table by using jackpot Id.
        var playerIds = new ArrayList<Integer>();
        getPlayerIdsFromPlrAscnByJkptId(jackpotId, playerIds);

        //get Player username and first by using above playerIds list
        var playerNames = new ArrayList<String>();
        getPlayerNameByPlayerIds(playerIds, playerNames);

        jackpotResponseModel.setPlayersList(playerNames);

        return jackpotResponseModel;
    }

    public void getJackpotsList(List<Integer> jackpotIds, List<PlayerJackpotResponseModel> playerJackpotResponseModelList){
        jackpotIds.forEach(jackpot -> {
            var paramMap = getJackpot(jackpot);
            var playerJackpotResponse = new PlayerJackpotResponseModel();
            playerJackpotResponse.setJackpotAmount(String.valueOf(paramMap.get("JACKPOT_AMOUNT")));
            playerJackpotResponse.setJackpotName(String.valueOf(paramMap.get("JACKPOT_NAME")));
            playerJackpotResponse.setJackpotId(String.valueOf(paramMap.get("JACKPOT_ID")));
            playerJackpotResponseModelList.add(playerJackpotResponse);
        });
    }

    public Map<Object,Object> getJackpot(Integer jackpotId){
        //retrieve jackpot details using jackpotId
        Optional<Jackpot> jackpotEntity = jackpotRepository.findOne(jackpotSpecs.getJackpotDetailsById(jackpotId));
        var paramMap = new HashMap<>();
        paramMap.put("JACKPOT_NAME", jackpotEntity.get().getJackpotName());
        paramMap.put("JACKPOT_AMOUNT", jackpotEntity.get().getJackpotMoney());
        paramMap.put("JACKPOT_ID", jackpotEntity.get().getJackpotId());
        paramMap.put("JACKPOT_CRTD_PLR", jackpotEntity.get().getJackpotCreatedPlayerId());

        return paramMap;
    }

    public void getPlayerNameByPlayerIds(ArrayList<Integer> playerIds, ArrayList<String> playerNames) {
        //get Player username and first by using above playerIds list
        List<Player> playerEntityList = playerSpecs.getPlayerDetailsByPlayerIds(playerIds);
        if(!CollectionUtils.isEmpty(playerEntityList)){
            for(Player playerEntity: playerEntityList){
                playerNames.add(playerEntity.getUserName());
            }
        }
    }

    public void getPlayerIdsFromPlrAscnByJkptId(Integer jackpotId, ArrayList<Integer> playerIds) {
        //Extract players from PlayerAssociation table by using jackpot Id.
        List<PlayerAssociation> playerAssoociationEntityList = playerAssociationRepository.findAll(playerAssociationSpecs.getPlayerAssociationByJackpotId(jackpotId));
        if(!CollectionUtils.isEmpty(playerAssoociationEntityList)){
            for(PlayerAssociation playerAscnEntity: playerAssoociationEntityList){
                playerIds.add(playerAscnEntity.getPlayerId());
            }
        }
    }

    public void getMatchDetailsByJackpotId(Integer jackpotId, ArrayList<MatchesResponseModel> matchResponseBodyList){
        List<Match> matchEntityList = matchRepository.findAll(matchSpecs.getMatchDetailsByJackpotId(jackpotId));
        if(!CollectionUtils.isEmpty(matchEntityList)){
            for(Match matchEntity: matchEntityList){
                var match = new MatchesResponseModel();
                match.setMatchName(matchEntity.getMatchName());
                match.setMatchNumber(matchEntity.getMatchNumber());
                match.setTeam1(matchEntity.getTeam1());
                match.setTeam2(matchEntity.getTeam2());
                match.setMaximumPoints(getMaxPointsByMatchId(matchEntity.getMatchId()));
                matchResponseBodyList.add(match);
            }
        }
    }

    public Integer getPlayerIdByPlayerName(String playerName){
        //get PlayerId by using userName
        Optional<Player> playerEntity = playerRepository.findOne(playerSpecs.getPlayerDetailsByUserName(playerName));
        if(playerEntity.isPresent()) {
            return playerEntity.get().getPlayerActivationId();
        }else{
            throw  new JackpotApiException(HttpStatus.NOT_FOUND, "No Data Found", "Player doesn't exist");
        }
    }

    private String getPlayerNameByPlayerId(Object playerId){
        //get userName by PlayerId
        Optional<Player> playerEntity = playerRepository.findOne(playerSpecs.getPlayerDetailsByPlayerId(playerId));
        if(playerEntity.isPresent()) {
            return playerEntity.get().getUserName();
        }else{
            throw  new JackpotApiException(HttpStatus.NOT_FOUND, "No Data Found", "Player doesn't exist");
        }
    }

    public Integer getMatchIdByMatchNumber(String matchNumber, Integer jackpotId){
        //get Match Id by using match Number & Jactkpot Id
        Optional<Match> matchEntity = matchRepository.findOne(matchSpecs.getMatchDetailsByJackpotIdAndMatchNumber(matchNumber, jackpotId));
        if(matchEntity.isPresent()) {
            return matchEntity.get().getMatchId();
        }else{
            throw  new JackpotApiException(HttpStatus.NOT_FOUND, "No Data Found", "Match doesn't exist");
        }
    }

    public String getPredictionDetails(Integer matchId){
        Optional<Prediction> predictionEntity = predictionRepository.findOne(predictionSpecs.getPredictionDetailsByMatchId(matchId));
        if(predictionEntity.isPresent()) {
            return predictionEntity.get().getPredictedTeam();
        }else{
            return "Not Predicted";
        }
    }

    public String getPredictionDetailsByMatchIdAndPlayerId(Integer matchId, Integer playerId){
        Optional<Prediction> predictionEntity = predictionRepository.findOne(predictionSpecs.getPredictionDetailsByMatchIdAndPlayerId(matchId, playerId));
        if(predictionEntity.isPresent()) {
            return predictionEntity.get().getPredictedTeam();
        }else{
            return "Not Predicted";
        }
    }

    private Integer getMaxPointsByMatchId(Integer matchId){
        List<Points> pointsEntityList = pointsRepository.findAll(pointsSpecs.getPointsByMatchId(matchId));
        if(!CollectionUtils.isEmpty(pointsEntityList)) {
            var maxPointsSet = new HashSet<Integer>();
            for(Points pointsEntity: pointsEntityList){
                maxPointsSet.add(pointsEntity.getMaximumPoints());
            }
            if(maxPointsSet.size() == 1){
                return 1;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public void getMatchNumbersByJackpotId(Integer jackpotId, List<String> matchNumbers){
        List<Match> matchEntityList = matchRepository.findAll(matchSpecs.getMatchDetailsByJackpotId(jackpotId));
        if(!CollectionUtils.isEmpty(matchEntityList)){
            for(Match matchEntity: matchEntityList){
                matchNumbers.add(matchEntity.getMatchNumber());
            }
        }
    }

    public Optional<Points> getMatchPointsByPlayerIdAndMatchId(Integer playerId, Integer matchId){
        Optional<Points> pointsEntity = pointsRepository.findOne(pointsSpecs.getPointsByPlayerIdAndMatchId(playerId, matchId));
        if(pointsEntity.isPresent()) {
            return pointsEntity;
        }else{
            return null;
        }
    }

}
