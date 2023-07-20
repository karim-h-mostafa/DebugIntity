package com.eita.signup.data.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String name;
    private String gender;
    private String birthDate;
    private String address;
    private String email;
    private String password;
}
