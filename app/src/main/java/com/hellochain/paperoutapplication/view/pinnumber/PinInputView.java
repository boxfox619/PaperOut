package com.hellochain.paperoutapplication.view.pinnumber;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hellochain.paperoutapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boxfox on 2017-09-09.
 */

public class PinInputView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private OnFinishEnterPin listener;

    private TextView tvMessage;
    private LinearLayout pinCheckLayout;
    private int pinLength = 4;
    private List<Integer> pins;

    public PinInputView(Context context) {
        this(context, null);
    }

    public PinInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initializeView(attrs);
    }

    public PinInputView setOnFinishListener(OnFinishEnterPin listener) {
        this.listener = listener;
        return this;
    }

    private void setErrorMessage(String msg) {
        tvMessage.setText(msg);
        tvMessage.setTextColor(Color.RED);
    }

    private void seMessage(String msg) {
        tvMessage.setText(msg);
        tvMessage.setTextColor(Color.BLACK);
    }

    private void initializeView(AttributeSet attrs) {
        pins = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_pin_number_input, this);
        tvMessage = view.findViewById(R.id.tv_message);
        pinCheckLayout = view.findViewById(R.id.layout_password_checker);

        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.PinInputKeyboard);
            this.pinLength = a.getInt(R.styleable.PinInputKeyboard_pinLength, 0);
        }

        for (int i = 0; i < pinLength; i++) {
            pinCheckLayout.addView(new PinCheckCircle(mContext));
        }

        TableLayout tableLayout = view.findViewById(R.id.layout_keyboard);
        TableRow row = new TableRow(mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 1; i <= 9; i++) {
            row.addView(new PinKeyboardButton(mContext, i + "", this));
            if (i % 3 == 0) {
                tableLayout.addView(row, i / 3 - 1);
                row = new TableRow(mContext);
                row.setLayoutParams(lp);
            }
        }
        row.addView(new View(mContext));
        row.addView(new PinKeyboardButton(mContext, "0", this));
        row.addView(new PinKeyboardButton(mContext, R.drawable.ic_backspace_black_24dp, new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBacksapce();
            }
        }));
        tableLayout.addView(row, 3);
    }

    private void onBacksapce() {
        if (pins.size() > 0) {
            ((PinCheckCircle) pinCheckLayout.getChildAt(pins.size() - 1)).setDeactive();
            pins.remove(pins.size() - 1);
        }
    }

    @Override
    public void onClick(View view) {
        if (pins.size() < pinLength) {
            int pin = Integer.valueOf(((TextView) ((View) view.getParent()).findViewById(R.id.keyboard_button_textview)).getText().toString());
            pins.add(pin);
            ((PinCheckCircle) pinCheckLayout.getChildAt(pins.size() - 1)).setActive();
            if (pins.size() == pinLength) {
                if (listener != null) {
                    String pinStr = "";
                    for (int pinItem : pins)
                        pinStr += pinItem;
                    listener.onFinish(pins);
                    listener.onFinish(pinStr);
                }
            }
        }
    }

    public abstract class OnFinishEnterPin {
        public void onFinish(List<Integer> pins) {}
        public void onFinish(String pinStr) {}
    }
}
