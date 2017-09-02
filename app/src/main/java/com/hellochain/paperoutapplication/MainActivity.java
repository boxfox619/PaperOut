package com.hellochain.paperoutapplication;

import android.app.ProgressDialog;
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
import com.hellochain.paperoutapplication.data.User;
import com.hellochain.paperoutapplication.view.paperlist.LinearListUitl;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private LinearListUitl.LinearList paperListView;
    private Button downloadBtn, sendBtn;
    private ImageView searchBtn;

    private List<String> printedPaperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar_main);

        paperListView = LinearListUitl.wrap((LinearLayout) findViewById(R.id.listView));
        searchBtn = (ImageView) findViewById(R.id.iv_search);
        searchBtn.setOnClickListener((view) -> {

        });

        downloadBtn = (Button) findViewById(R.id.btn_download);
        sendBtn = (Button) findViewById(R.id.btn_send);

        downloadBtn.setOnClickListener((view) -> {
            if (paperListView.isItemSelected()) {
                String item = (String) paperListView.getSelectedItem();

            } else {

            }
        });
        sendBtn.setOnClickListener((view) -> {
            Toast.makeText(this, "아직 지원되지 않는 기능입니다!", Toast.LENGTH_LONG).show();
        });
        loadPrintedPapers();
    }

    private void loadPrintedPapers(){
        Realm realm = Realm.getDefaultInstance();
        AQuery aq = new AQuery(this);
        Map<String, Object> params = new HashMap<>();
        params.put("user", realm.where(User.class).findFirst().getId());
        aq.ajax(getString(R.string.server_host)+getString(R.string.url_get_printed_paper_list),params, JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray arr, AjaxStatus status) {
                List<String> papers = new ArrayList<String>();
                for(int i = 0 ; i < arr.length(); i++){
                    try {
                        papers.add(arr.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                paperListView.loadPapers(papers);
            }
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
