package com.eita.signup.ui.signupScreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.eita.signup.data.repos.SignupRepo;
import com.eita.signup.data.requests.SignupRequest;
import com.eita.signup.data.responses.BaseResponse;
import com.eita.signup.utils.di.qualifires.Loading;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import timber.log.Timber;


@Getter
@HiltViewModel
public class SignupViewModel extends ViewModel {
    @Inject
    SignupRepo signupRepo;
    @Inject
    MutableLiveData<BaseResponse> signupResponseMutableLiveData;
    @Loading
    @Inject
    MutableLiveData<Boolean> loadingVisibility;
    @Inject
    public SignupViewModel() {

    }

    public void signup(SignupRequest request) {
        signupRepo.disposable.add(
                Observable.just(request)
                        .doOnNext(loading -> loadingVisibility.setValue(true))
                        .observeOn(Schedulers.io())
                        .flatMap(passedParameter -> signupRepo.signup(passedParameter))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> loadingVisibility.setValue(false))
                        .takeWhile(this::responseValidation)
                        .subscribe(signupResponseMutableLiveData::setValue, Timber::e)
        );
    }

    private boolean responseValidation(BaseResponse response) {
//      TODO  i should handel what happens and pass the errors to show in pop up dialog or something
//        TODO but due to fake api i can not do this as it is auto generating responses no matter what i passed to it;
        return true;
    }
}
