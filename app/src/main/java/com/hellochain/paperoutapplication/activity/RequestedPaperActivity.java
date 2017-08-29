package com.hellochain.paperoutapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.hellochain.paperoutapplication.R;

public class RequestedPaperActivity extends AppCompatActivity {
    private TextView tvKind, tvCompany, tvDuration;
    private Button btnLater, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_paper);

        tvKind = (TextView)findViewById(R.id.tv_kind);
        tvCompany = (TextView)findViewById(R.id.tv_company);
        tvDuration = (TextView)findViewById(R.id.tv_duration);

        btnLater = (Button)findViewById(R.id.btn_later);
        btnSend = (Button)findViewById(R.id.btn_send);
        btnLater.setOnClickListener((view)->{});
        btnSend.setOnClickListener((view)->{});
    }
}
