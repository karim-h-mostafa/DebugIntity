package com.eita.login.utils.helpers;

import android.content.Context;
import android.widget.TextView;


import com.eita.login.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;


public class Validation {

    private final Context mContext;

    @Inject
    public Validation(@ApplicationContext Context context) {
        mContext = context;
    }

    private String getResourceString(int res) {
        return mContext.getString(res);
    }

    private final String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final Pattern pattern = Pattern.compile(regex);

    public boolean isNotEmpty(TextView textView) {
        if (textView.getText().toString().trim().equals("") || textView.getText() == null) {
            textView.setError(mContext.getString(R.string.empty_field));
            return false;
        }
        return true;
    }
    public boolean isValidJSON(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmail(TextView textView) {
        if (!isNotEmpty(textView))
            return false;
        if(!pattern.matcher(textView.getText()).matches()){
            textView.setError(getResourceString(R.string.email_incorrect));
            return false;
        }
        return true;
    }

    public boolean isPhone(TextView textView) {
        String text = textView.getText().toString().trim();
        if(!text.startsWith("01")){
            textView.setError("يجب ان يبدأ رقم الهاتف ب 01");
            return false;
        }
        if (text.length()!=11)
        {
            textView.setError("يجب ان يتكون طول الرقم من 11 رقم");
            return false;
        }
        return true;
    }
}
