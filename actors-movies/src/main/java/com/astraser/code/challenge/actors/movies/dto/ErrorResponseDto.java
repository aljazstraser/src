package com.astraser.code.challenge.actors.movies.dto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
public class ErrorResponseDto {


    private String apiPath;

    private HttpStatus errorCode;

    private String errorMessage;

    private LocalDateTime errorTime;

}
