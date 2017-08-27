package com.hellochain.paperoutapplication.view.tablayout;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hellochain.paperoutapplication.R;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by boxfox on 2017-08-26.
 */

public class TabCycleLayout extends LinearLayout {
    private OnTabSelectedListener onTabSelected = null;
    private FrameLayout content;
    private LinearLayout footer;
    private int prevPage, currentPage, pageCount;
    private Map<Integer, OnTabSelectedListener> listeners;

    public TabCycleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.TabCycleLayout);
        this.pageCount=a.getInt(R.styleable.TabCycleLayout_tabSize, 0);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_tab_cycle, this);
        this.content = (FrameLayout) findViewById(R.id.content);
        this.footer = findViewById(R.id.footer);
        listeners = new HashMap<>();
        renderFooter();
    }

    private void renderFooter() {
        if (footer.getChildCount() == 0)
            for (int i = 0; i < pageCount; i++) {
                footer.addView(createTabCircle(i == currentPage));
            }
        else{
            footer.removeView(footer.getChildAt(prevPage));
            footer.addView(createTabCircle(false), prevPage);
            footer.removeView(footer.getChildAt(currentPage));
            footer.addView(createTabCircle(true), currentPage);
        }
    }

    private View createTabCircle(boolean check) {
        ScalableLayout sl = new ScalableLayout(getContext(), 3, 3);
        View view = new View(getContext());
        view.setBackground(getResources().getDrawable((check)?R.drawable.circle_bright:R.drawable.circle_dark));
        sl.addView(view, 1f, 1.5f, 1f, 1f);
        return sl;
    }

    public int currentPage() {
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
            prevPage = currentPage;
            currentPage = page;
            notifyPageStateChange();
        }
    }

    public void setOnPageSelectListener(int page, OnTabSelectedListener listener) {
        listeners.put(page, listener);
        if (page == currentPage) {
            setContentView(listener.onSelected());
        }
    }

    private void notifyPageStateChange() {
        if (listeners.get(currentPage) != null) {
            setContentView(listeners.get(currentPage).onSelected());
        }
        renderFooter();
    }

    private void setContentView(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public interface OnTabSelectedListener {
        public Fragment onSelected();
    }
}
