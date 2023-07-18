package com.eita.signup.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;


import com.eita.signup.databinding.DatePickerBinding;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class ModernDatePicker extends Dialog implements CalendarView.OnDateChangeListener {
    private DatePickerBinding mBinding;
    private ConfirmClickListener confirmClickListener;
    private Long selectedDate;
    private DatePicker picker;


    @Inject
    public ModernDatePicker(@ActivityContext Context context) {
        super(context);
        mBinding = DatePickerBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        picker=mBinding.bpCalendar;
        ColorDrawable colorDrawable = new ColorDrawable(Color.alpha(0));
        InsetDrawable insetDrawable = new InsetDrawable(colorDrawable, 64);
        getWindow().setBackgroundDrawable(insetDrawable);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        selectedDate = mBinding.bpCalendar.getDate();
//        mBinding.bpCalendar.setOnDateChangeListener(this);
//        mBinding.bpCalendar.getChildAt(0).setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBinding.bpCalendar.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
               /* Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                selectedDate = calendar.getTime().getTime();*/
            });
        }
        else {

        }
        mBinding.btnConfirm.setOnClickListener(this::onConfirmClicked);
        mBinding.btnCancel.setOnClickListener(view -> dismiss());
    }

    private void onConfirmClicked(View view) {
        if (confirmClickListener != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(picker.getYear(), (picker.getMonth() ), picker.getDayOfMonth());
            selectedDate = calendar.getTime().getTime();
            confirmClickListener.selectedDate(selectedDate);

        }
    }

    public void setConfirmClickListener(ConfirmClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        selectedDate = calendar.getTime().getTime();
    }


    public interface ConfirmClickListener {
        void selectedDate(Long selectedDate);
    }
}
