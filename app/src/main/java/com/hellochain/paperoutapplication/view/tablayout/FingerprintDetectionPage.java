package com.hellochain.paperoutapplication.view.tablayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.R;

import org.json.JSONArray;

/**
 * Created by boxfox on 2017-08-27.
 */

public class FingerprintDetectionPage extends Fragment{
    private Handler handler;
    private int num;

    public static FingerprintDetectionPage getInstance(int num, Handler handler){
        FingerprintDetectionPage fragment = new FingerprintDetectionPage();
        fragment.handler = handler;
        fragment.num = num;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fingerprint_dection, container, false);
        return view;
    }
}
