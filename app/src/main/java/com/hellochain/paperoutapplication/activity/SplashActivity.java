package com.hellochain.paperoutapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 || checkPermission()){
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
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode==PERMISSION_REQUEST_CODE){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                nextStep();
            } else {
                Toast.makeText(this,getString(R.string.warning_need_permission), Toast.LENGTH_LONG).show();

            }

        }

    }
}
