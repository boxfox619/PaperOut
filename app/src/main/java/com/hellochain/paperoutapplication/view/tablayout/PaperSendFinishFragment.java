package com.hellochain.paperoutapplication.view.tablayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellochain.paperoutapplication.R;

/**
 * Created by boxfox on 2017-08-27.
 */

public class PaperSendFinishFragment extends Fragment {
    private Handler handler;
    private int num;

    public static PaperSendFinishFragment getInstance(int num, Handler handler){
        PaperSendFinishFragment fragment = new PaperSendFinishFragment();
        fragment.handler = handler;
        fragment.num = num;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_finish_send_paper, container, false);
        view.findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = handler.obtainMessage();
                msg.what = num;
                handler.sendMessage(msg);
            }
        });
        return view;
    }
}
