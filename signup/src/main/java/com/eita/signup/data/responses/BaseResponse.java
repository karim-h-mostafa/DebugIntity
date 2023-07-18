package com.eita.signup.data.responses;

import lombok.Data;

@Data
public class BaseResponse {
    private int statusCode;
    private boolean success;
    private String message;
}
