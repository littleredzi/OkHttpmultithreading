package com.example.administrator.okhttpmultithreading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button action;
    private ProgressBar bar;
    private DownloadManager instance;
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin657android1040.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
         instance  = DownloadManager.getInstance();
        instance.add(wechatUrl, new DownloadListner() {
            @Override
            public void onFinished() {
                Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float progress) {
                float v = progress * 100;
                Log.e("progress", v+"");
                bar.setProgress((int)v);
        }

            @Override
            public void onPause() {
                Toast.makeText(MainActivity.this, "暂停了!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void initView() {
        action = (Button) findViewById(R.id.action);
        bar = (ProgressBar) findViewById(R.id.bar);

        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                if (!instance.isDownloading(wechatUrl)){
                    instance.download(wechatUrl);
                    action.setText("暂停");
                }else{
                    action.setText("下载");
                    instance.pause(wechatUrl);
                }
                break;
        }
    }
}
