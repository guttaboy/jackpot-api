package com.ent.jackpot.exception;

import com.ent.jackpot.model.ErrorDetailModel;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class JackpotApiExceptionHandler {

    ErrorDetailModel apiError;
    
    @ExceptionHandler
    public ResponseEntity<ErrorDetailModel> handleException(Throwable ex){
        ResponseEntity<ErrorDetailModel> responseEntity = null;
        apiError = new ErrorDetailModel();
        
        if(ex instanceof JackpotApiException){
            var errorDetail = ErrorDetailModel.builder()
                    .detail(((JackpotApiException) ex).getDetail())
                    .title(((JackpotApiException) ex).getTitle())
                    .status(((JackpotApiException) ex).getStatus())
                    .build();
            responseEntity = new ResponseEntity<>(errorDetail, ((JackpotApiException) ex).getStatus());
        }
        return responseEntity;
    }
}
