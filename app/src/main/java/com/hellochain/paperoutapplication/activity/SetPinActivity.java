package com.hellochain.paperoutapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hellochain.paperoutapplication.R;
import com.hellochain.paperoutapplication.view.pinnumber.PinInputView;

import io.realm.Realm;

public class SetPinActivity extends AppCompatActivity {
    private PinInputView pinInputView;

    private String pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        pinInputView = (PinInputView) findViewById(R.id.pin_input_view);
        pinInputView.setOnFinishListener(new PinInputView.OnFinishEnterPin(){
            @Override
            public void onFinish(String pinStr) {
                if(pin==null){
                    pin = pinStr;
                    pinInputView.setMessage(getString(R.string.signup_request_password_confirm));
                    pinInputView.reset();
                }else if(!pin.equals(pinStr)){
                    pin = null;
                    pinInputView.setErrorMessage(getString(R.string.signup_error_password));
                    pinInputView.reset();
                }else{
                    finish();
                }
            }
        });
    }
}
