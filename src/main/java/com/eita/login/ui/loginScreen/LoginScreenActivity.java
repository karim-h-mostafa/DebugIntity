package com.eita.login.ui.loginScreen;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.eita.login.data.requests.LoginRequest;
import com.eita.login.data.responses.LoginResponse;
import com.eita.login.databinding.ActivityLoginScreenBinding;
import com.eita.login.dialogs.LoadingDialog;

import com.eita.login.utils.helpers.Validation;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginScreenActivity extends AppCompatActivity {

    private ActivityLoginScreenBinding mBinding;
    private LoginViewModel mViewModel;
    @Inject
    LoadingDialog loadingDialog;
    @Inject
    Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.getLoginResponseMutableLiveData().observe(this, this::onLoginResponse);
        mViewModel.getLoadingVisibility().observe(this, this::onLoadingVisibilityChanged);
        mBinding.btnLogin.setOnClickListener(this::onLoginClicked);
    }

    private void onLoadingVisibilityChanged(Boolean status) {
        if (status)
            loadingDialog.show();
        else loadingDialog.dismiss();
    }

    private void onLoginResponse(LoginResponse loginResponse) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        super.onDestroy();
    }

    private void onLoginClicked(View view) {
        if (valid()) {
            mViewModel.login(LoginRequest.builder().build());
        }
    }

    private boolean valid() {
        return validation.isEmail(mBinding.etEmail) && validation.isNotEmpty(mBinding.etPassword);
    }
}