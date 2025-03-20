package com.example.BasicCrudOperations.Exception;

public class EmailNotFoundException extends  RuntimeException{

    public EmailNotFoundException(String message){
        super(message);
    }
}
