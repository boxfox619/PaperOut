package com.hellochain.paperoutapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hellochain.paperoutapplication.activity.PaperPrintActivity;
import com.hellochain.paperoutapplication.data.Paper;
import com.hellochain.paperoutapplication.data.User;
import com.hellochain.paperoutapplication.view.paperlist.LinearListUitl;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private LinearListUitl.LinearList paperListView;
    private Button downloadBtn, sendBtn;
    private ImageView searchBtn;

    private List<String> printedPaperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar_main);

        paperListView = LinearListUitl.wrap((LinearLayout) findViewById(R.id.listView));
        searchBtn = (ImageView) findViewById(R.id.iv_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        downloadBtn = (Button) findViewById(R.id.btn_download);
        sendBtn = (Button) findViewById(R.id.btn_send);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paperListView.isItemSelected()) {
                    String item = (String) paperListView.getSelectedItem();
                    String paperUrl = Realm.getDefaultInstance().where(Paper.class).findFirst().getPaperUrl();
                    showProgressDialog(paperUrl);

                } else {
                    Snackbar.make(((View) paperListView.getView().getParent().getParent()), getString(R.string.msg_need_choose_paper), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!paperListView.isItemSelected()) {
                    Snackbar.make(((View) paperListView.getView().getParent().getParent()), getString(R.string.msg_need_choose_paper), Snackbar.LENGTH_SHORT).show();
                } else {
                    String item = (String) paperListView.getSelectedItem();
                    View view = getLayoutInflater().inflate(R.layout.layout_dialog_email, null);
                    final EditText etEmail = (EditText) view.findViewById(R.id.et_email);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle(getResources().getString(R.string.dialog_please_enter_email));
                    dialog.setView(view);
                    dialog.setPositiveButton("전송", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!isEmailValid(etEmail.getText().toString())) {
                                Snackbar.make(((View) paperListView.getView().getParent().getParent()), getString(R.string.error_worng_email), Snackbar.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(MainActivity.this, PaperPrintActivity.class);
                                intent.putExtra("type", PaperPrintActivity.ACTION_TYPE_SEND);
                                intent.putExtra("email", etEmail.getText());
                                intent.putExtra("target", paperListView.getSelectedItem().toString());
                                startActivity(intent);
                            }
                        }
                    });
                    dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
            }
        });
        loadPrintedPapers();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPrintedPapers();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void showPasswordInputDialog() {
    }

    private void loadPrintedPapers() {
        RealmResults<Paper> papers = Realm.getDefaultInstance().where(Paper.class).findAll();
        List<String> paperList = new ArrayList<>();
        for (Paper paper : papers) {
            paperList.add(paper.getPapaerName());
        }
        paperListView.loadPapers(paperList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_print_paper:
                startActivity(new Intent(this, PaperPrintActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ProgressDialog progressBar;

    private void showProgressDialog(String url) {
        progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setMessage("다운로드중");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setCancelable(true);

        final DownloadFilesTask downloadTask = new DownloadFilesTask(MainActivity.this);
        downloadTask.execute(url);

        progressBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
    }

    private class DownloadFilesTask extends AsyncTask<String, String, Boolean> {

        private Context context;

        public DownloadFilesTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected Boolean doInBackground(String... string_url) {
            int len;
            InputStream input = null;
            OutputStream output = null;
            URLConnection connection = null;
            try {
                URL url = new URL(string_url[0]);
                connection = url.openConnection();
                connection.connect();

                int totalSize = connection.getContentLength();
                input = new BufferedInputStream(url.openStream());
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File outputFile = new File(path, string_url[0].substring(string_url[0].lastIndexOf("/"), string_url[0].length()));
                output = new FileOutputStream(outputFile);
                byte data[] = new byte[1024];
                long downloadedSize = 0;
                while ((len = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return false;
                    }
                    downloadedSize += len;
                    if (totalSize > 0) {
                        float per = ((float) downloadedSize / totalSize) * 100;
                        String str = "Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int) per + "%)";
                        publishProgress("" + (int) ((downloadedSize * 100) / totalSize), str);
                    }
                    output.write(data, 0, len);
                }
                output.flush();
                output.close();
                input.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            progressBar.setIndeterminate(false);
            progressBar.setMax(100);
            progressBar.setProgress(Integer.parseInt(progress[0]));
            progressBar.setMessage(progress[1]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            progressBar.dismiss();
            if (success) {
                Toast.makeText(getApplicationContext(), "다운로드 완료되었습니다.", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), "다운로드 에러", Toast.LENGTH_LONG).show();
        }

    }
}
