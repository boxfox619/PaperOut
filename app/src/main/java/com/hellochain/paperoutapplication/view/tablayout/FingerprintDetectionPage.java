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
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getResources().getString(R.string.dialog_please_enter_text));
        dialog.setView(view);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String password = etPassword.getText().toString();
            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("TESAT", "tasdasfsa");
            }
        });
        dialog.show();
    }
}
