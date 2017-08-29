package com.hellochain.paperoutapplication.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hellochain.paperoutapplication.R;
import com.hellochain.paperoutapplication.view.tablayout.FingerprintDetectionPage;
import com.hellochain.paperoutapplication.view.tablayout.PaperDownloadFragment;
import com.hellochain.paperoutapplication.view.tablayout.PaperSelectPage;
import com.hellochain.paperoutapplication.view.tablayout.TabCycleLayout;

public class PaperPrintActivity extends AppCompatActivity {
    private TabCycleLayout tabCycleLayout;
    private Handler pageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_print);

        this.pageHandler = new PageHandler();

        this.tabCycleLayout = (TabCycleLayout) findViewById(R.id.tab_cycle_layout);
        this.tabCycleLayout.setOnPageSelectListener(0, () -> PaperSelectPage.getInstance(0, pageHandler));
        this.tabCycleLayout.setOnPageSelectListener(1, () -> FingerprintDetectionPage.getInstance(1, pageHandler));
        this.tabCycleLayout.setOnPageSelectListener(2, () -> PaperDownloadFragment.getInstance(2, pageHandler));
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

                    break;

                default:
                    break;
            }
        }

    }

}
