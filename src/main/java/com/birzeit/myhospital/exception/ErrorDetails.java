package com.birzeit.myhospital.exception;

import com.birzeit.myhospital.services.LocalDateFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorDetails {
    private String timeStamp;
    private String message;
    private String details;
    private int status;

    public ErrorDetails(LocalDateTime timeStamp, String message, String details, int status) {
        this.timeStamp = LocalDateFormatter.formatDateAndTime(timeStamp);
        this.message = message;
        this.details = details;
        this.status = status;
    }
}