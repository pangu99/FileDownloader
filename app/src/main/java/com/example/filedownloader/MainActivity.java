package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private Button stopButton;
    private TextView text;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload(view);
            }
        });
        stopButton = findViewById(R.id.stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDownload(view);
            }
        });
        text = findViewById(R.id.textView);
    }

    public void mockFileDownloader(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                startButton.setText("Downloading...");
            }
        });

        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress+10){
            if (stopThread) {
                break;
            }

            final int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    text.setText("Download Progress: " + finalDownloadProgress + "%");
                }
            });

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                startButton.setText("Start");
                text.setText("");
            }
        });


    }

    public void startDownload(View view){
        stopThread = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mockFileDownloader();
            }
        }).start();
    }

    public void stopDownload(View view){
        stopThread = true;
    }
}
