package com.hellochain.paperoutapplication.view.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.R;
import com.hellochain.paperoutapplication.view.paperlist.LinearListUitl;
import com.hellochain.paperoutapplication.view.paperlist.ListItem;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by boxfox on 2017-08-26.
 */

public class PaperSelectPage extends Fragment {
    private static final String[] paperList = {"재학증명서", "성적증명서", "성적증명서(석차표기)", "수료증명서", "장학금수혜증명서", "교육비납입증명서", "학적부사본", "교명변경확인증명서"};
    private LinearListUitl.LinearList listView;
    private Handler handler;

    private Button submit;
    private int num;

    public static PaperSelectPage getInstance(int num, Handler handler) {
        PaperSelectPage fragment = new PaperSelectPage();
        fragment.handler = handler;
        fragment.num = num;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_select_paper, container, false);
        listView = LinearListUitl.wrap((LinearLayout) view.findViewById(R.id.listView));
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = handler.obtainMessage();
                msg.what = num;
                msg.obj = listView.getSelectedItem();
                handler.sendMessage(msg);
            }
        });

        listView.loadPapers(paperList);

        AQuery aq = new AQuery(getContext());
        aq.ajax(getResources().getString(R.string.server_host) + getResources().getString(R.string.url_get_paper_list), JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray result, AjaxStatus status) {
                if (status.getCode() == 200) {

                } else {

                }
            }
        });
        return view;
    }
}
