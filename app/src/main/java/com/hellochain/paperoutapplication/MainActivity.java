package com.hellochain.paperoutapplication;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.activity.PaperPrintActivity;
import com.hellochain.paperoutapplication.view.paperlist.LinearListUitl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private LinearListUitl.LinearList paperListView;
    private Button downloadBtn, sendBtn;
    private ImageView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar_main);

        paperListView = LinearListUitl.wrap((LinearLayout) findViewById(R.id.listView));
        paperListView.loadPapers(getPrintedPapers());
        searchBtn = (ImageView) findViewById(R.id.iv_search);
        searchBtn.setOnClickListener((view) -> {
        });

        downloadBtn = (Button) findViewById(R.id.btn_download);
        sendBtn = (Button) findViewById(R.id.btn_send);

        downloadBtn.setOnClickListener((view) -> {
            if (paperListView.isItemSelected()) {
                String item = (String) paperListView.getSelectedItem();

                String requestUrl = getString(R.string.server_host) + getString(R.string.url_paper_download) + "?paper=" + item;

                File ext = Environment.getExternalStorageDirectory();
                File target = new File(ext, "paperout/" + getCurrentDate() + ".pdf");

                /*MaterialDialog dialog =  new MaterialDialog.Builder(this)
                        .title(R.string.progress_dialog)
                        .content(R.string.please_wait)
                        .progress(true, 0)
                        .show();*/
                AQuery aq = new AQuery(this);
                aq.download(requestUrl, target, new AjaxCallback<File>() {
                    public void callback(String url, File file, AjaxStatus status) {
                        if (file != null) {
                            //다운로드 성공
                        } else {
                            //다운로드 실패
                        }
                    }

                });
            } else {

            }
        });
        sendBtn.setOnClickListener((view) -> {
            Toast.makeText(this, "아직 지원되지 않는 기능입니다!", Toast.LENGTH_LONG).show();
        });
    }

    private static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

    private String[] getPrintedPapers() {

        return new String[0];
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
