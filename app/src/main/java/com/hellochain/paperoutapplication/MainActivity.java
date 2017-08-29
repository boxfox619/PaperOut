package com.hellochain.paperoutapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hellochain.paperoutapplication.activity.PaperPrintActivity;

public class MainActivity extends AppCompatActivity {
    private Button downloadBtn, sendBtn;
    private ImageView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar_main);

        searchBtn = (ImageView) findViewById(R.id.iv_search);
        searchBtn.setOnClickListener((view) -> {
        });

        downloadBtn = (Button) findViewById(R.id.btn_download);
        sendBtn = (Button) findViewById(R.id.btn_send);

        downloadBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, PaperPrintActivity.class);
            intent.putExtra("type", PaperPrintActivity.ACTION_TYPE_DOWNLOAD);
            //intent.putExtra("target", "");
            startActivity(intent);
        });
        sendBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, PaperPrintActivity.class);
            intent.putExtra("type", PaperPrintActivity.ACTION_TYPE_SEND);
            //intent.putExtra("target", "");
            startActivity(intent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_print_paper:
                startActivity(new Intent(this, PaperPrintActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
