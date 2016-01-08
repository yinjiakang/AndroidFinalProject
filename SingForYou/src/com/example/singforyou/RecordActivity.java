package com.example.singforyou;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class RecordActivity extends Activity {
    private Record record = new Record();
    String fileNamePrefix;
    String fileName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        record.init();
        fileNamePrefix = RecordActivity.this.getExternalFilesDir(null).toString() + "/";

        if ( !record.isSDCardExist() ) {
            Log.e("Record", "SD card does not exist!");
        }

        final ImageButton confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record == null) {
                    return;
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            record.upload(fileName, id);
                            RecordActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    confirm.setVisibility(View.INVISIBLE);
                                }
                            });
                            Log.e("upload", id + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                            RecordActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(RecordActivity.this, "Fail to upload the audio, please check your network",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
                thread.start();

                progressDialog = ProgressDialog.show(RecordActivity.this, "Uploading", "Please wait...");
            }
        });

        final ImageView voice = (ImageView) findViewById(R.id.voice);
        ImageButton start = (ImageButton) findViewById(R.id.start);
        start.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fileName = fileNamePrefix + id + ".3gp";
                record.startRecording(fileName);
                voice.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.INVISIBLE);
                Log.e("bt", "pressed!");
                return true;
            }
        });

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    voice.setVisibility(View.INVISIBLE);
                    // http://blog.csdn.net/qinde025/article/details/6828723
                    // Delay 880ms.
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            record.stopRecording();
                            confirm.setVisibility(View.VISIBLE);
                            record.startPlaying(fileName);
                        }
                    }, 800);
                    Log.e("bt", "released!");
                }
                return false;
            }
        });

        /* Download
        fileName = fileNamePrefix + id + ".3gp";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.e("before", "before");
                    record.download(fileName, id);
                    RecordActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                    record.startPlaying(fileName);
                    //Log.e("after", "after");
                } catch (Exception e) {
                    e.printStackTrace();
                    RecordActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
        thread.start();

        progressDialog = ProgressDialog.show(RecordActivity.this, "Downloading", "Please wait...");
        */



    }

    @Override
    protected void onPause() {
        super.onPause();
        record.stopRecording();
        record.stopPlaying();
    }
}
