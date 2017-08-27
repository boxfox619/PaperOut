package com.hellochain.paperoutapplication.view.tablayout;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hellochain.paperoutapplication.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by boxfox on 2017-08-26.
 */

public class TabCycleLayout extends LinearLayout {
    private OnTabSelectedListener onTabSelected = null;
    private FrameLayout content;
    private LinearLayout footer;
    private int currentPage, pageCount;
    private Map<Integer, OnTabSelectedListener> listeners;

    public TabCycleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pageCount = attrs.getAttributeIntValue(R.attr.tabSize, 0);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_tab_cycle, this);
        this.content = (FrameLayout) findViewById(R.id.content);
        this.footer = findViewById(R.id.footer);
        listeners = new HashMap<>();
        renderFooter();
    }

    private void renderFooter(){
        footer.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            footer.addView(createTabCircle(i == currentPage));
        }
    }

    private View createTabCircle(boolean check) {
        View view = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10 , 10);
        layoutParams.setMargins(15, 0, 15, 0);
        view.setLayoutParams(layoutParams);
        view.setBackgroundDrawable(getResources().getDrawable((check) ? R.drawable.circle_bright : R.drawable.circle_dark));
        return view;
    }

    public int currentPage(){
        return currentPage;
    }

    public void next() {
        setPage(currentPage + 1);
    }

    public void prev() {
        setPage(currentPage - 1);
    }

    public void setPage(int page) {
        if (page >= 0 || page < pageCount) {
            currentPage = page;
            notifyPageStateChange();
        }
    }

    public void setOnPageSelectListener(int page, OnTabSelectedListener listener) {
        listeners.put(page, listener);
        if(page==currentPage){
            setContentView(listener.onSelected());
        }
    }

    private void notifyPageStateChange() {
        renderFooter();
        if (listeners.get(currentPage) != null) {
            setContentView(listeners.get(currentPage).onSelected());
        }
    }

    private void setContentView(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public interface OnTabSelectedListener {
        public Fragment onSelected();
    }
}
