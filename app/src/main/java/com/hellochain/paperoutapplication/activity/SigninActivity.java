package com.hellochain.paperoutapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    private EditText et_id, et_password;
    private Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        et_id = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerBtn = (Button) findViewById(R.id.btn_register);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AQuery aq = new AQuery(SigninActivity.this);
                aq.ajax(getResources().getString(R.string.server_host)+getResources().getString(R.string.url_login), getParams(), JSONObject.class, new AjaxCallback<JSONObject>(){
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        if(status.getCode() == 200){

                        }else{
                            Snackbar.make(((View)loginBtn.getParent().getParent()), getResources().getString(R.string.msg_login_fail), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", et_id.getText().toString());
        params.put("password", et_password.getText().toString());
        return params;
    }
}
