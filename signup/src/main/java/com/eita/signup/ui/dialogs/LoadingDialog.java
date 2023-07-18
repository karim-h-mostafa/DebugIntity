package com.eita.signup.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import com.eita.signup.databinding.DialogLoadingBinding;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class LoadingDialog extends Dialog {
    private DialogLoadingBinding mBinding;

    @Inject
    public LoadingDialog(@ActivityContext Context context) {
        super(context);
        mBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        setCancelable(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.alpha(0));
        InsetDrawable insetDrawable = new InsetDrawable(colorDrawable, 64);
        getWindow().setBackgroundDrawable(insetDrawable);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }
}
