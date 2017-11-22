package com.hellochain.paperoutapplication.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText et_university, et_name, et_number, et_code;
    private Button submitBtn, requestCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_university = (EditText) findViewById(R.id.et_university);
        (findViewById(R.id.tv_university)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_university.requestFocus();
            }
        });
        et_name = (EditText) findViewById(R.id.et_name);
        (findViewById(R.id.tv_name)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_name.requestFocus();
            }
        });
        et_number = (EditText) findViewById(R.id.et_number);
        (findViewById(R.id.tv_number)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_number.requestFocus();
            }
        });
        et_code = (EditText) findViewById(R.id.et_code);
        (findViewById(R.id.tv_code)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_code.requestFocus();
            }
        });
        requestCodeBtn = (Button) findViewById(R.id.btn_request_code);
        requestCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //인증번호 전송요청 구현 필요


            }
        });
        submitBtn = (Button) findViewById(R.id.btn_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AQuery aq = new AQuery(SignupActivity.this);
                aq.ajax(getResources().getString(R.string.server_host) + getResources().getString(R.string.url_register), getParams(), JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        if (status.getCode() == 200) {
                            startActivity(new Intent(SignupActivity.this, SetPinActivity.class));
                        } else {
                            Snackbar.make(((View) submitBtn.getParent().getParent()), getResources().getString(R.string.msg_register_fail), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap();
        params.put("University", et_university.getText().toString());
        params.put("Name", et_name.getText().toString());
        params.put("Number", Integer.valueOf(et_number.getText().toString()));
        return params;
    }
}
