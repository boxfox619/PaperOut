package com.hellochain.paperoutapplication.view.tablayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONArray;

/**
 * Created by boxfox on 2017-08-27.
 */

public class FingerprintDetectionPage extends Fragment {
    private Handler handler;
    private int num;

    public static FingerprintDetectionPage getInstance(int num, Handler handler) {
        FingerprintDetectionPage fragment = new FingerprintDetectionPage();
        fragment.handler = handler;
        fragment.num = num;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fingerprint_dection, container, false);
        view.findViewById(R.id.enter_password_direct).setOnClickListener((v) -> showPasswordInputDialog());
        return view;
    }

    private void showPasswordInputDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_edittext, null);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);

        DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(view))
                .setExpanded(false)
                .create();
        dialog.show();
    }
}
