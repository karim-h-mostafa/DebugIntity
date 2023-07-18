package com.eita.ui.successfulSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.eita.login.ui.loginScreen.LoginScreenActivity;
import com.eita.modulrizedapp.databinding.ActivitySuccessfulSignupBinding;
import com.eita.ui.successfulLogin.SuccessfulLoginActivity;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SuccessfulSignupActivity extends AppCompatActivity {

    private ActivitySuccessfulSignupBinding mBinding;
    private ActivityResultLauncher<Intent> startLoginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySuccessfulSignupBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.btnLogin.setOnClickListener(this::onLoginClicked);
        startLoginModule = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onLoginResult);

    }
    private void onLoginClicked(View view) {
        startLoginModule.launch(new Intent(this, LoginScreenActivity.class));
    }
    private void onLoginResult(ActivityResult result) {
        startActivity(new Intent(this, SuccessfulLoginActivity.class));
    }

}