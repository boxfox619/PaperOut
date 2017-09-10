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

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;
import com.hellochain.paperoutapplication.R;
import com.hellochain.paperoutapplication.view.pinnumber.PinInputView;

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
        View view = inflater.inflate(R.layout.layout_fingerprint_detection, container, false);
        view.findViewById(R.id.enter_password_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordInputDialog();
            }
        });
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
        PinInputView pinInputView = (PinInputView)view.findViewById(R.id.pin_input_view);
        pinInputView.setOnFinishListener(new PinInputView.OnFinishEnterPin() {
            @Override
            public void onFinish(String pinStr) {
                /*if(success)
                    sendResultMsg(true);*/
            }
        });
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getResources().getString(R.string.dialog_please_enter_pin));
        dialog.setView(view);
        dialog.show();
    }
}
