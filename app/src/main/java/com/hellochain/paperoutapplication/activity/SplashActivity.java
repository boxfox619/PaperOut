package com.hellochain.paperoutapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hellochain.paperoutapplication.MainActivity;
import com.hellochain.paperoutapplication.R;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Realm.init(this);

        if(checkPermission()){
            nextStep();
        }
    }

    private void nextStep(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    nextStep();
                } else {
                    Toast.makeText(this,getString(R.string.warning_need_permission), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
