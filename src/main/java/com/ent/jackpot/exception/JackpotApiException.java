package com.ent.jackpot.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class JackpotApiException extends RuntimeException {

    private final HttpStatus status;
    private final String title;
    private final String detail;


    public JackpotApiException(HttpStatus status, String title, String detail) {
        this.status = status;
        this.title = title;
        this.detail = detail;
    }
}
