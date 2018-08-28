package com.dofun.sxl.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dofun.sxl.R;

import java.lang.reflect.Method;

public class FractionView extends LinearLayout {
    EditText etNumerator;
    EditText etDenominator;

    private String numerator = "";
    private String denominator = "";

    public FractionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_fraction_layout, this);
        initView();
    }

    private void initView() {
        etNumerator = findViewById(R.id.tv_numerator);
        etDenominator = findViewById(R.id.tv_denominator);
        disableShowInput(etDenominator);
        disableShowInput(etNumerator);
    }

    private void disableShowInput(final EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(editText.length());
            }
        });
    }

    public String getNumerator() {
        return numerator;
    }

    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    public String getDenominator() {
        return denominator;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    public EditText getEtNumerator() {
        return etNumerator;
    }

    public void setEtNumerator(EditText etNumerator) {
        this.etNumerator = etNumerator;
    }

    public EditText getEtDenominator() {
        return etDenominator;
    }

    public void setEtDenominator(EditText etDenominator) {
        this.etDenominator = etDenominator;
    }

    public String getFraction() {
        return numerator + "/" + denominator;
    }
}
