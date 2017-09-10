package com.hellochain.paperoutapplication.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hellochain.paperoutapplication.R;

public class SetPasswordActivity extends AppCompatActivity {
    private EditText etPassword, etPasswordConfirm;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        etPassword = (EditText)findViewById(R.id.et_password);
        findViewById(R.id.tv_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPassword.requestFocus();
            }
        });
        etPasswordConfirm = (EditText)findViewById(R.id.et_password_confirm);
        findViewById(R.id.tv_password_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPasswordConfirm.requestFocus();
            }
        });
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
