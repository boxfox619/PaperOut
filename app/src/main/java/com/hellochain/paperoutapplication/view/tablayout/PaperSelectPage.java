package com.hellochain.paperoutapplication.view.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.R;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by boxfox on 2017-08-26.
 */

public class PaperSelectPage extends Fragment {
    private static final String[] paperList = {"재학증명서", "성적증명서", "성적증명서(석차표기)", "수료증명서", "장학금수혜증명서", "교육비납입증명서", "학적부사본", "교명변경확인증명서"};
    private LinearLayout listView;
    private Handler handler;

    private Button submit;
    private String mSelectedItem;

    public static PaperSelectPage getInstance(Handler handler){
        PaperSelectPage fragment = new PaperSelectPage();
        fragment.handler = handler;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_select_paper, container, false);
        listView = (LinearLayout) view.findViewById(R.id.listView);
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener((target) -> {
            Message msg = handler.obtainMessage();
            msg.obj = mSelectedItem;
            handler.sendMessage(msg);
        });

        loadPapers(paperList);

        AQuery aq = new AQuery(getContext());
        aq.ajax(getResources().getString(R.string.server_host) + getResources().getString(R.string.get_paper_list), JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray result, AjaxStatus status) {
                if (status.getCode() == 200) {

                } else {

                }
            }
        });
        return view;
    }


    private void loadPapers(String[] items) {
        for (String item : items) {
            View view = new ListItem(getContext(), item, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View target) {
                    if(((ListItem)target).isSelected())return;
                        for(int i = 0 ; i < listView.getChildCount();i++){
                            if(((ListItem)listView.getChildAt(i)).isSelected()){
                                View result = replaceView(listView.getChildAt(i), false);
                                result.setOnClickListener(this);
                                break;
                            }
                        }
                        View result = replaceView(target, true);
                        result.setOnClickListener(this);
                        mSelectedItem = ((ListItem) target).getText();
                    }
            });
            listView.addView(view);
        }
    }

    private View replaceView(View target, boolean check){
        int idx = listView.indexOfChild(target);
        listView.removeView(target);
        View result = new ListItem(getContext(), ((ListItem) target).getText(), check);
        listView.addView(result, idx);
        return result;
    }

    private class ListItem extends LinearLayout {
        private boolean isSelected = false;

        public ListItem(Context context, String text, boolean isSelected) {
            super(context);
            inflate(context, R.layout.list_item, this);
            if (isSelected) {
                this.setBackgroundColor(getResources().getColor(R.color.list_item_selected));
            }else{
                this.setBackground(getResources().getDrawable(R.drawable.border_layout));
            }
            ((TextView) findViewById(R.id.textView)).setText(text);
            this.isSelected = isSelected;
        }

        public String getText() {
            return ((TextView) findViewById(R.id.textView)).getText().toString();
        }

        @Override
        public boolean isSelected() {
            return isSelected;
        }

    }
}
