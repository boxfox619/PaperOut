package com.hellochain.paperoutapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hellochain.paperoutapplication.R;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Realm.init(this);
    }
}
