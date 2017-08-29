package com.hellochain.paperoutapplication.view.tablayout;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;
import com.hellochain.paperoutapplication.R;

import org.json.JSONArray;

/**
 * Created by boxfox on 2017-08-27.
 */

public class FingerprintDetectionPage extends Fragment {
    private static final int MAX_ERROR_COUNT = 5;
    private Handler handler;
    private int num;

    private int count;
    private TextView guideTextView;

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
        guideTextView = (TextView) view.findViewById(R.id.tv_guide);
        Reprint.authenticate(new AuthenticationListener() {
            public void onSuccess(int moduleTag) {
                sendResultMsg(true);
            }

            public void onFailure(AuthenticationFailureReason failureReason, boolean fatal,
                                  CharSequence errorMessage, int moduleTag, int errorCode) {
                if (failureReason.equals(AuthenticationFailureReason.LOCKED_OUT)) return;
                if (failureReason.equals(AuthenticationFailureReason.AUTHENTICATION_FAILED))
                    if (count == MAX_ERROR_COUNT) {
                        sendResultMsg(false);
                    } else {
                        showErrorMsg();
                    }
                else {
                    setFingerPrintUnable();
                }
            }
        });
        return view;
    }

    private void setFingerPrintUnable() {
        guideTextView.setText(getResources().getString(R.string.error_unable_fingerprint));
    }

    private void sendResultMsg(boolean result) {
        Message msg = handler.obtainMessage();
        msg.what = num;
        msg.obj = result;
        handler.sendMessage(msg);
    }

    private void showErrorMsg() {
        count += 1;
        guideTextView.setText(getResources().getString(R.string.error_retry_fingerprint));
        guideTextView.setTextColor(Color.RED);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                guideTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }, 1000);
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
