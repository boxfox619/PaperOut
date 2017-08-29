package com.hellochain.paperoutapplication.view.paperlist;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellochain.paperoutapplication.R;

public class ListItem extends LinearLayout {
        private boolean isSelected = false;

        public ListItem(Context context, String text, boolean isSelected, int layoutId) {
            super(context);
            inflate(context, layoutId, this);
            if (isSelected) {
                this.setBackgroundColor(getResources().getColor(R.color.list_item_selected));
            }else{
                this.setBackground(getResources().getDrawable(R.drawable.border_layout));
            }
            ((TextView) findViewById(R.id.textView)).setText(text);
            this.isSelected = isSelected;
        }

        public void setOnClickListener(int id, View.OnClickListener listener){
            findViewById(id).setOnClickListener(listener);
        }

        public String getText() {
            return ((TextView) findViewById(R.id.textView)).getText().toString();
        }

        @Override
        public boolean isSelected() {
            return isSelected;
        }

    }