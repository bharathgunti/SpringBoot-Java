package com.example.BasicCrudOperations.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request){
        Map<String,Object> excep=new HashMap<>();
        excep.put("timeStamp", LocalDateTime.now());
        excep.put("error","User not Found");
        excep.put("Status", HttpStatus.NOT_FOUND.value());
        excep.put("message",ex.getMessage());
        excep.put("path",request.getDescription(false));

        return new ResponseEntity<>(excep,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Object> hanldeEmailNotFound(EmailNotFoundException ex,WebRequest request){
        Map<String,Object>exception=new HashMap<>();
        exception.put("TimeStap",LocalDateTime.now());
        exception.put("Status",HttpStatus.NOT_FOUND.value());
        exception.put("Error","User Not Found");
        exception.put("Message",ex.getMessage());
        exception.put("Path",request.getDescription(false));
        return new ResponseEntity<>(exception,HttpStatus.NOT_FOUND);
    }
}
