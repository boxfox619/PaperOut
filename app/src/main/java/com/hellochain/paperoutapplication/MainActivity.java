package com.hellochain.paperoutapplication;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hellochain.paperoutapplication.view.tablayout.FingerprintDetectionPage;
import com.hellochain.paperoutapplication.view.tablayout.PaperDownloadFragment;
import com.hellochain.paperoutapplication.view.tablayout.PaperSelectPage;
import com.hellochain.paperoutapplication.view.tablayout.TabCycleLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
