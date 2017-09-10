package com.hellochain.paperoutapplication.view.pinnumber;

import android.content.Context;
import android.graphics.PorterDuff;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.hellochain.paperoutapplication.R;

import org.w3c.dom.Text;

/**
 * Created by boxfox on 2017-09-09.
 */

public class PinKeyboardButton extends RelativeLayout {

    private Context mContext;

    public PinKeyboardButton(Context context, String text, OnClickListener listener) {
        super(context);

        this.mContext = context;
        initializeView(text, listener);
    }

    public PinKeyboardButton(Context context, int drawable, OnClickListener listener) {
        super(context);
        this.mContext = context;
        initializeView(drawable, listener);
    }

    private void initializeView(Object value, OnClickListener listener) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PinKeyboardButton view = (PinKeyboardButton) inflater.inflate(R.layout.view_pin_keyboard_button, this);
        findViewById(R.id.pin_code_keyboard_button_ripple).setOnClickListener(listener);

        TextView textView = (TextView) view.findViewById(R.id.keyboard_button_textview);
        if (value instanceof String) {
            textView.setText((String) value);
        } else {
            textView.setVisibility(INVISIBLE);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.keyboard_button_imageview);
        if (value instanceof Integer) {
            imageView.setImageDrawable(getResources().getDrawable((Integer) value));
            imageView.setVisibility(VISIBLE);
            imageView.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN);
        }

    }

}
