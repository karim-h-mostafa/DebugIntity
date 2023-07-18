package com.eita.login.data.responses;

import com.eita.login.data.models.Login;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends BaseResponse{
    private Login data;


}
