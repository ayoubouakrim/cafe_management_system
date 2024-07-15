package com.cafe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class CafeUtils {
    private CafeUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + message + "\"}", httpStatus);

    }

    public static String getUuid() {
        Date today = new Date();
        long time = today.getTime();
        return "BILL-"+time;
    }

}
