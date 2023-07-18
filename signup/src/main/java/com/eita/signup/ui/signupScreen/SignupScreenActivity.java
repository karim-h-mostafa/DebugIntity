package com.eita.signup.ui.signupScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.eita.signup.R;
import com.eita.signup.data.requests.SignupRequest;
import com.eita.signup.data.responses.BaseResponse;
import com.eita.signup.databinding.ActivitySignupScreenBinding;
import com.eita.signup.ui.dialogs.LoadingDialog;
import com.eita.signup.ui.dialogs.ModernDatePicker;
import com.eita.signup.utils.helpers.Validation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignupScreenActivity extends AppCompatActivity {

    ActivitySignupScreenBinding mBinding;
    private SignupViewModel mViewModel;
    @Inject
    Validation validation;
    @Inject
    LoadingDialog loadingDialog;
    @Inject
    ModernDatePicker datePicker;
    @Inject
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySignupScreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        mViewModel.getLoadingVisibility().observe(this, this::onLoadingVisibilityChanged);
        mViewModel.getSignupResponseMutableLiveData().observe(this, this::onSignupResponse);
        ArrayList<String> editText = new ArrayList<>();
        editText.add(getString(R.string.male));
        editText.add(getString(R.string.female));
        mBinding.etGender.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_selection, editText));
        mBinding.btnSignup.setOnClickListener(this::onSignupClicked);
        mBinding.etBirthday.setOnClickListener(this::onBirthdateClicked);
        datePicker.setConfirmClickListener(this::onDateSelected);
    }
    public void onDateSelected(Long date) {
        mBinding.etBirthday.setText(simpleDateFormat.format(date));
        datePicker.dismiss();

    }
    private void onBirthdateClicked(View view) {
        datePicker.show();
    }

    private void onLoadingVisibilityChanged(Boolean status) {
        if (status)
            loadingDialog.show();
        else loadingDialog.dismiss();
    }

    private void onSignupResponse(BaseResponse response) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        super.onDestroy();
    }

    private void onSignupClicked(View view) {
        if (valid()) {
            mViewModel.signup(
                    SignupRequest.builder()
                            .name(mBinding.etName.getText().toString())
                            .gender(mBinding.etGender.getText().toString())
                            .birthDate(mBinding.etBirthday.getText().toString())
                            .address(mBinding.etAddress.getText().toString())
                            .email(mBinding.etEmail.getText().toString())
                            .password(mBinding.etPassword.getText().toString())
                            .build()
            );
        }
    }

    private boolean valid() {
        return
                validation.isNotEmpty(mBinding.etName) &&
                        validation.isNotEmpty(mBinding.etGender) &&
                        validation.isNotEmpty(mBinding.etBirthday) &&
                        validation.isNotEmpty(mBinding.etAddress) &&
                        validation.isNotEmpty(mBinding.etPassword) &&
                        validation.isEmail(mBinding.etEmail);
    }
}