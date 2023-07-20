package com.eita.signup.data.sources;




import com.eita.signup.data.requests.SignupRequest;
import com.eita.signup.data.responses.BaseResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("signup")
    Observable<BaseResponse> signup(@Body SignupRequest request);
}
