package com.ent.jackpot.util;

import com.ent.jackpot.entity.Jackpot;
import com.ent.jackpot.entity.Player;
import com.ent.jackpot.exception.JackpotApiException;
import com.ent.jackpot.jpaspecs.JackpotSpecs;
import com.ent.jackpot.jpaspecs.PlayerSpecs;
import com.ent.jackpot.model.JackpotActivationModel;
import com.ent.jackpot.model.MatchRequestModel;
import com.ent.jackpot.model.PlayerActivationModel;
import com.ent.jackpot.model.PredictionModel;
import com.ent.jackpot.repository.JackpotRepository;
import com.ent.jackpot.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidatorUtil {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerSpecs playerSpecs;

    @Autowired
    JackpotRepository jackpotRepository;

    @Autowired
    JackpotSpecs jackpotSpecs;

    public void validatePlayerActivationModel(PlayerActivationModel playerActivationModel){
        validateIObjectNull(playerActivationModel.getFirstName(), "First Name is Missing");
        validateIObjectNull(playerActivationModel.getLastName(), "Last Name is Missing");
        validateIObjectNull(playerActivationModel.getUserName(), "userName is Missing");
//        validateIObjectNull(playerActivationModel.getEmail(), "email is Missing");
        checkUserNameDuplicate(playerActivationModel.getUserName());
    }

    public void validateJackpotActivationModel(JackpotActivationModel jackpotActivationModel){
        validateIObjectNull(jackpotActivationModel.getJackpotGroupName(), "Jackpot Group Name is Missing");
        validateIObjectNull(jackpotActivationModel.getJackpotAmount(), "Jackpot Amount is Missing");
        validateIObjectNull(jackpotActivationModel.getPlayersList(), "Players List is Missing");
        validateExistingPlayers(jackpotActivationModel.getPlayersList());
    }

    public void validateUserName(String userName){
        validateIObjectNull(userName, "User Name is missing");
        Optional<Player> playerEntity = playerRepository.findOne(playerSpecs.getPlayerDetailsByUserName(userName));
        if(!playerEntity.isPresent()) {
            throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", "Player Doesn't Exist");
        }
    }

    public void validateKey(String key){
        validateIObjectNull(key, "Key is missing");
    }

    public void validateJackpot(Integer jackpotId){
        Optional<Jackpot> playerEntity = jackpotRepository.findOne(jackpotSpecs.getJackpotDetailsById(jackpotId));
        if(!playerEntity.isPresent()) {
            throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", "Jackpot Doesn't Exist");
        }
    }

    public void validateMatchRequestModel(MatchRequestModel matchRequestModel){
        validateIObjectNull(matchRequestModel.getMatchName(), "Match Name is Missing");
        validateIObjectNull(matchRequestModel.getMatchNumber(), "Match Number is Missing");
        validateIObjectNull(matchRequestModel.getTeam1(), "Team 1 is Missing");
        validateIObjectNull(matchRequestModel.getTeam2(), "Team 2 is Missing");
    }

    public void validatePredictionModel(PredictionModel predictionModel){
        validateIObjectNull(predictionModel.getPredictedTeam(), "Team Name must be provided for prediction");
        validateIObjectNull(predictionModel.getMatchNumber(), "Match Number must be provided for prediction");
    }

    private void validateIObjectNull(Object object, String errorMessage){
        if(Objects.isNull(object) || object == ""){
            throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", errorMessage);
        }
    }

    private void validateExistingPlayers(List<String> playersList){
        for(String userName: playersList){
            validateUserName(userName);
        }
    }

    private void checkUserNameDuplicate(String username){
        Optional<Player> playerEntity = playerRepository.findOne(playerSpecs.getPlayerDetailsByUserName(username));
        if(playerEntity.isPresent()){
            throw new JackpotApiException(HttpStatus.BAD_REQUEST, "Invalid Input Error", "username already exists, try another username");
        }
    }

}
