package com.eita.ui.startScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.eita.login.ui.loginScreen.LoginScreenActivity;

import com.eita.modulrizedapp.databinding.ActivityStartScreenBinding;
import com.eita.signup.ui.signupScreen.SignupScreenActivity;
import com.eita.ui.successfulLogin.SuccessfulLoginActivity;
import com.eita.ui.successfulSignup.SuccessfulSignupActivity;

public class StartScreenActivity extends AppCompatActivity {

    private ActivityStartScreenBinding mBinding;
    private ActivityResultLauncher<Intent> startLoginModule;
    private ActivityResultLauncher<Intent> startSignupModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityStartScreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        startLoginModule = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onLoginResult);
        startSignupModule = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onSignupResult);
        mBinding.btnLogin.setOnClickListener(this::onLoginClicked);
        mBinding.btnSignUp.setOnClickListener(this::onSignupClicked);

    }

    private void onSignupClicked(View view) {
        startSignupModule.launch(new Intent(this, SignupScreenActivity.class));
    }

    private void onLoginClicked(View view) {
        startLoginModule.launch(new Intent(this, LoginScreenActivity.class));
    }

    private void onSignupResult(ActivityResult result) {
        startActivity(new Intent(this, SuccessfulSignupActivity.class));
    }

    private void onLoginResult(ActivityResult result) {
        startActivity(new Intent(this, SuccessfulLoginActivity.class));
    }
}