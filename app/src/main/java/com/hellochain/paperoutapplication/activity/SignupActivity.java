package com.hellochain.paperoutapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private EditText et_university, et_name, et_number;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_university = (EditText) findViewById(R.id.et_university);
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        submitBtn = (Button) findViewById(R.id.btn_submit);
        submitBtn.setOnClickListener((view) -> {
            AQuery aq = new AQuery(this);
            aq.ajax(getResources().getString(R.string.server_host)+getResources().getString(R.string.url_register), getParams(), JSONObject.class, new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    if(status.getCode()==200){

                    }else{

                    }
                }
            });
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
