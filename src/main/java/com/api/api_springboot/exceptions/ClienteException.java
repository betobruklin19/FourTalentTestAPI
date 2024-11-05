package com.api.api_springboot.exceptions;

import com.api.api_springboot.services.ClienteService;

public class ClienteException extends RuntimeException{

    public ClienteException(String message){
        super(message);
    }
}
