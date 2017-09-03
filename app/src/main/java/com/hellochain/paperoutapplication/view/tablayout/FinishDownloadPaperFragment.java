package com.hellochain.paperoutapplication.view.tablayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hellochain.paperoutapplication.R;

import java.io.File;

/**
 * Created by boxfox on 2017-09-03.
 */

public class FinishDownloadPaperFragment extends Fragment {
    private File file;
    private Handler handler;
    private int num;

    public static FinishDownloadPaperFragment getInstance(int num, Handler handler){
        FinishDownloadPaperFragment fragment = new FinishDownloadPaperFragment();
        fragment.handler = handler;
        fragment.num = num;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_finish_download_paper, container, false);
        ((Button)view.findViewById(R.id.btn_view)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage();
            }
        });

        ((Button)view.findViewById(R.id.btn_finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return view;
    }

    public void setFile(File file){
        this.file = file;
    }

    public void showImage(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(file.toURI().toString()),"image/*");
        startActivity(intent);
    }

    public void finish(){
        Message msg = handler.obtainMessage();
        msg.what = num;
        handler.sendMessage(msg);
    }

}
