package com.eita.signup.data.repos;


import com.eita.signup.data.requests.SignupRequest;
import com.eita.signup.data.responses.BaseResponse;
import com.eita.signup.data.sources.ApiInterface;
import com.eita.signup.utils.di.qualifires.Signup;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignupRepo {
    @Inject
    @Signup
    public CompositeDisposable disposable;
    @Inject
    public SignupRepo() {
    }

    @Inject
    ApiInterface apiInterface;


    public Observable<BaseResponse> signup(SignupRequest passedParameter) {
        return apiInterface.signup(passedParameter);
    }
}
