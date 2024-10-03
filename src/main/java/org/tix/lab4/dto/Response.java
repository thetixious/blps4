package org.tix.lab4.dto;

import lombok.Data;

@Data
public class Response {
    private String message;


    public Response(String message){
        this.message = message;
    }


}
