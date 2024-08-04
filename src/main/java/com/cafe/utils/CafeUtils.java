package com.cafe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;

public class CafeUtils {


    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + message + "\"}", httpStatus);

    }

    public static String getUuid() {
        Date today = new Date();
        long time = today.getTime();
        return "BILL-"+time;
    }
    public static  Boolean isFileExist(String path) {
        File file = new File(path);
        return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
    }

}
