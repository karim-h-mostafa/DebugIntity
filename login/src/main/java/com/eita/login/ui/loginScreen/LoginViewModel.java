package com.eita.login.ui.loginScreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eita.login.data.repos.LoginRepo;
import com.eita.login.data.requests.LoginRequest;
import com.eita.login.data.responses.BaseResponse;
import com.eita.login.data.responses.LoginResponse;
import com.eita.login.utils.di.qualifires.Loading;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import timber.log.Timber;


@Getter
@HiltViewModel
public class LoginViewModel extends ViewModel {
    @Inject
    LoginRepo loginRepo;
    @Inject
    MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    @Loading
    @Inject
    MutableLiveData<Boolean> loadingVisibility;
    @Inject
    public LoginViewModel() {

    }

    public void login(LoginRequest request) {
        loginRepo.disposable.add(
                Observable.just(request)
                        .doOnNext(loading -> loadingVisibility.setValue(true))
                        .observeOn(Schedulers.io())
                        .flatMap(passedParameter -> loginRepo.login(passedParameter))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> loadingVisibility.setValue(false))
                        .takeWhile(this::responseValidation)
                        .subscribe(loginResponseMutableLiveData::setValue, Timber::e)
        );
    }

    private boolean responseValidation(BaseResponse response) {
//      TODO  i should handel what happens and pass the errors to show in pop up dialog or something
//        TODO but due to fake api i can not do this as it is auto generating responses no matter what i passed to it;
        return true;
    }
}
