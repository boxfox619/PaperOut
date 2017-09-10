package com.hellochain.paperoutapplication.view.pinnumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.hellochain.paperoutapplication.R;

/**
 * Created by boxfox on 2017-09-09.
 */

public class PinCheckCircle extends RelativeLayout {
    private View circle;

    public PinCheckCircle(Context context) {
        super(context);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_pin_check_circle, this);

        circle = view.findViewById(R.id.view_circle);
        circle.setBackgroundResource(R.drawable.circle_dark);
    }

    public void setActive(){
        circle.setBackgroundResource(R.drawable.circle_active);
    }

    public void setDeactive(){
        circle.setBackgroundResource(R.drawable.circle_dark);
    }

}
