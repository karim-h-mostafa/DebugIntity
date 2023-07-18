package com.eita.login.data.sources;

import com.eita.login.data.requests.LoginRequest;
import com.eita.login.data.responses.LoginResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("login")
    Observable<LoginResponse> login(@Body LoginRequest request);
}
