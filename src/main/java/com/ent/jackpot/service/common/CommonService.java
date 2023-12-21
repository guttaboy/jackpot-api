package com.ent.jackpot.service.common;

import com.ent.jackpot.entity.Jackpot;
import com.ent.jackpot.entity.Match;
import com.ent.jackpot.entity.Player;
import com.ent.jackpot.entity.PlayerAssociation;
import com.ent.jackpot.jpaspecs.JackpotSpecs;
import com.ent.jackpot.jpaspecs.MatchSpecs;
import com.ent.jackpot.jpaspecs.PlayerAssociationSpecs;
import com.ent.jackpot.jpaspecs.PlayerSpecs;
import com.ent.jackpot.model.JackpotResponseModel;
import com.ent.jackpot.model.MatchesResponseModel;
import com.ent.jackpot.model.PlayerJackpotResponseModel;
import com.ent.jackpot.repository.JackpotRepository;
import com.ent.jackpot.repository.MatchRepository;
import com.ent.jackpot.repository.PlayerAssociationRepository;
import com.ent.jackpot.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
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

    public JackpotResponseModel getJackpotDetails(Integer jackpotId){
        //retrieve jackpot details using jackpotId
        var jackpotResponseModel = new JackpotResponseModel();
        var paramMap = getJackpot(jackpotId);
        jackpotResponseModel.setJackpotName(String.valueOf(paramMap.get("JACKPOT_NAME")));
        jackpotResponseModel.setJackpotAmount(String.valueOf(paramMap.get("JACKPOT_AMOUNT")));

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
                matchResponseBodyList.add(match);
            }
        }
    }

}
