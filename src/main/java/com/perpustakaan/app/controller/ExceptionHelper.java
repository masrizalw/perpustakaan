package com.perpustakaan.app.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perpustakaan.app.exception.UserException;

@ControllerAdvice @ResponseBody
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = { UserException.class })
    protected ResponseEntity<Map<String, String>> handleUserException(UserException e) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error_message", e.getMessage());
        response.put("timestamp", time());
        logger.error("Error {} at {}", e.getMessage(), response.get("timestamp"));
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(response);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    protected ResponseEntity<Map<String, String>> handleSQLException(DataIntegrityViolationException e) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error_message", e.getRootCause().getMessage());
        response.put("timestamp", time());
        logger.error("Error {} at {}", e.getRootCause().getMessage(), response.get("timestamp"));
        // logger.error("Error {} at {}",e.getMessage(), response.get("timestamp"));
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(response);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    protected ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException e) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error_message", e.getMessage());
        response.put("timestamp", time());
        logger.error("Error {} at {}", e.getMessage(), response.get("timestamp"));
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(response);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<?> handleException(Exception e) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error_message", e.getMessage());
        response.put("timestamp", time());
        logger.error("Error {} at {}", e.getMessage(), response.get("timestamp"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String time() {
        LocalDateTime ld = LocalDateTime.now();
        return ld.toLocalDate().toString() + "@" + ld.toLocalTime().toString();
    }

}
