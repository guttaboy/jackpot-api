package com.ent.jackpot.controller;

import com.ent.jackpot.model.CreateResponseModel;
import com.ent.jackpot.model.WinnerCreateModel;
import com.ent.jackpot.service.WinnerService;
import com.ent.jackpot.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/jackpot/v1")
@Slf4j
public class WinnerController {

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    WinnerService winnerService;

    @PostMapping(value = "/updateWinner", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateResponseModel updateWinner(
            Integer jackpotId,
            String matchNumber,
            @RequestBody WinnerCreateModel winnerCreateModel) throws Exception {

        validatorUtil.validateJackpot(jackpotId);
        //TODO: validate Match Number

        return winnerService.updateWinnerDetails(jackpotId, matchNumber, winnerCreateModel);
    }

}
