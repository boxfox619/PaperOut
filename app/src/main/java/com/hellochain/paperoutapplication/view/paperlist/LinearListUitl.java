package com.hellochain.paperoutapplication.view.paperlist;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.hellochain.paperoutapplication.R;

/**
 * Created by boxfox on 2017-08-29.
 */

public class LinearListUitl {

    public static LinearList wrap(LinearLayout layout, int id) {
        return new LinearList(layout, id);
    }

    public static LinearList wrap(LinearLayout layout) {
        return new LinearList(layout, R.layout.list_item);
    }

    public static class LinearList {
        private final int listItemLayoutId;
        private LinearLayout layout;
        private Object mSelectedItem;

        protected LinearList(LinearLayout layout, int id) {
            listItemLayoutId = id;
            this.layout = layout;
        }

        public ListItem findItem(String text) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                ListItem item = (ListItem) layout.getChildAt(i);
                if (item.getText().equals(text)) {
                    return item;
                }
            }
            return null;
        }

        public void loadPapers(String[] items) {
            layout.removeAllViews();
            for (String item : items) {
                View view = new ListItem(layout.getContext(), item, false, listItemLayoutId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View target) {
                        if (((ListItem) target).isSelected()) return;
                        for (int i = 0; i < layout.getChildCount(); i++) {
                            if (((ListItem) layout.getChildAt(i)).isSelected()) {
                                View result = replaceView(layout.getChildAt(i), false);
                                result.setOnClickListener(this);
                                break;
                            }
                        }
                        View result = replaceView(target, true);
                        result.setOnClickListener(this);
                        mSelectedItem = ((ListItem) target).getText();
                    }
                });
                layout.addView(view);
            }
        }

        private View replaceView(View target, boolean check) {
            int idx = layout.indexOfChild(target);
            layout.removeView(target);
            View result = new ListItem(layout.getContext(), ((ListItem) target).getText(), check, listItemLayoutId);
            layout.addView(result, idx);
            return result;
        }

        public Object getSelectedItem() {
            return mSelectedItem;
        }
    }
}
