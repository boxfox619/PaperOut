package com.hellochain.paperoutapplication.view.tablayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellochain.paperoutapplication.R;

/**
 * Created by boxfox on 2017-08-27.
 */

public class PaperDownloadFragment extends Fragment {
    private Handler handler;
    private int num;

    public static PaperDownloadFragment getInstance(int num, Handler handler){
        PaperDownloadFragment fragment = new PaperDownloadFragment();
        fragment.handler = handler;
        fragment.num = num;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_download_paper, container, false);
        return view;
    }
}
