package com.eita.login.data.repos;

import com.eita.login.data.requests.LoginRequest;
import com.eita.login.data.responses.LoginResponse;
import com.eita.login.data.sources.ApiInterface;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginRepo {
    @Inject
    public CompositeDisposable disposable;
    @Inject
    public LoginRepo() {
    }

    @Inject
    ApiInterface apiInterface;


    public Observable<LoginResponse> login(LoginRequest passedParameter) {
        return apiInterface.login(passedParameter);
    }
}
