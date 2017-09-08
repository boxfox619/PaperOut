package com.hellochain.paperoutapplication.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.github.ajalt.reprint.core.Reprint;
import com.hellochain.paperoutapplication.R;
import com.hellochain.paperoutapplication.view.tablayout.FingerprintDetectionPage;
import com.hellochain.paperoutapplication.view.tablayout.FinishDownloadPaperFragment;
import com.hellochain.paperoutapplication.view.tablayout.PaperPrintFinishFragment;
import com.hellochain.paperoutapplication.view.tablayout.PaperSelectPage;
import com.hellochain.paperoutapplication.view.tablayout.PaperSendFinishFragment;
import com.hellochain.paperoutapplication.view.tablayout.TabCycleLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaperPrintActivity extends AppCompatActivity {
    public static final int ACTION_TYPE_PRINT = 0;
    public static final int ACTION_TYPE_DOWNLOAD = 1;
    public static final int ACTION_TYPE_SEND = 2;
    private TabCycleLayout tabCycleLayout;
    private Handler pageHandler;

    private int actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_print);
        Reprint.initialize(this);

        this.pageHandler = new PageHandler();

        this.tabCycleLayout = (TabCycleLayout) findViewById(R.id.tab_cycle_layout);
        this.tabCycleLayout.setOnPageSelectListener(0, new TabCycleLayout.OnTabSelectedListener() {
            @Override
            public Fragment onSelected() {
                return PaperSelectPage.getInstance(0, pageHandler);
            }
        });
        this.tabCycleLayout.setOnPageSelectListener(1, new TabCycleLayout.OnTabSelectedListener() {
            @Override
            public Fragment onSelected() {
                return FingerprintDetectionPage.getInstance(1, pageHandler);
            }
        });
        this.tabCycleLayout.setOnPageSelectListener(2, new TabCycleLayout.OnTabSelectedListener() {
            @Override
            public Fragment onSelected() {
                return PaperPrintFinishFragment.getInstance(2, pageHandler);
            }
        });
        init();
    }

    private void init() {
        Intent intent = getIntent();
        this.actionType = intent.getIntExtra("type", ACTION_TYPE_PRINT);
        if (actionType != ACTION_TYPE_PRINT) {
            this.tabCycleLayout.next();
            switch (actionType) {
                case ACTION_TYPE_DOWNLOAD:
                    //do somting
                    this.tabCycleLayout.setOnPageSelectListener(2, new TabCycleLayout.OnTabSelectedListener() {
                        @Override
                        public Fragment onSelected() {
                            return PaperPrintFinishFragment.getInstance(2, pageHandler);
                        }
                    });
                    break;
                case ACTION_TYPE_SEND:
                    //do somting
                    this.tabCycleLayout.setOnPageSelectListener(2, new TabCycleLayout.OnTabSelectedListener() {
                        @Override
                        public Fragment onSelected() {
                            return PaperSendFinishFragment.getInstance(2, pageHandler);
                        }
                    });
                    break;
            }
        }
    }

    private View secondPage() {
        return getLayoutInflater().inflate(R.layout.layout_select_paper, null);
    }

    private View thirdPage() {
        return getLayoutInflater().inflate(R.layout.layout_select_paper, null);
    }

    class PageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (tabCycleLayout.currentPage()) {
                case 0:
                    if (msg.obj == null) {
                        Snackbar.make(tabCycleLayout, getResources().getString(R.string.msg_need_choose_paper), Snackbar.LENGTH_LONG).show();
                    } else {
                        tabCycleLayout.next();
                    }
                    break;
                case 1:
                    if (actionType == ACTION_TYPE_DOWNLOAD) {
                        downloadPaper();
                    } else if (actionType == ACTION_TYPE_SEND) {
                        sendPaper();
                    } else {
                        requestPrintPaper();
                    }
                    break;
                case 2:
                    finish();
                    break;
            }
        }
    }

    private void requestPrintPaper() {

    }

    private void sendPaper() {
        String email = getIntent().getStringExtra("email");
        String item = getIntent().getStringExtra("item");
        if (item == null) {
            Toast.makeText(this, getString(R.string.err_unknown_item), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String requestUrl = getString(R.string.server_host) + getString(R.string.url_paper_send) + "?email=" + email + "&paper=" + item;
        AQuery aq = new AQuery(this);
        aq.ajax(requestUrl, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if(status.getCode() == 200){
                    tabCycleLayout.next();
                }else{
                    Toast.makeText(PaperPrintActivity.this, getString(R.string.fail_send_paper), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void downloadPaper() {
        String item = getIntent().getStringExtra("item");
        if (item == null) {
            Toast.makeText(this, getString(R.string.err_unknown_item), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String requestUrl = getString(R.string.server_host) + getString(R.string.url_paper_download) + "?paper=" + item;

        File ext = Environment.getExternalStorageDirectory();
        final File target = new File(ext, "paperout/" + item + getCurrentDate() + ".png");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("증명서를 다운받고 있습니다..");

        AQuery aq = new AQuery(this);
        aq.download(requestUrl, target, new AjaxCallback<File>() {
            public void callback(String url, File file, AjaxStatus status) {
                dialog.dismiss();
                if (file != null) {
                    tabCycleLayout.next();
                    ((FinishDownloadPaperFragment) tabCycleLayout.getCurrentTab()).setFile(target);
                } else {
                    AlertDialog.Builder resultDialog = new AlertDialog.Builder(PaperPrintActivity.this);
                    resultDialog.setTitle("증명서 다운로드 실패");
                    resultDialog.setMessage("증명서를 다운로드 받지 못했습니다. 다시 시도해 주세요");
                    resultDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                }
            }
        });
    }

    private static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

}
