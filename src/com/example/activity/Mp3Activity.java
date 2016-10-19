package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * android.os.Environment  读取系统存储目录的文件
 *  android.media.MediaPlayer 系统媒体播放器

 * android.telephony.TelephonyManager  电话管理器
 * android.telephony.PhoneStateListener  电话状态监听器
 */
public class Mp3Activity extends Activity {
    private static String TAG = "MainActivity";
    private EditText nameText;  //mp3文件名称
    private String path;    //文件路径
    private MediaPlayer mediaPlayer;
    private boolean pause;  //暂停标志
    private int position;   //播放进度
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mediaPlayer = new MediaPlayer();
        nameText = (EditText)findViewById(R.id.filename);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    //电话状态监听器
    private final class MyPhoneListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    if(mediaPlayer.isPlaying()){
                        position = mediaPlayer.getCurrentPosition();
                        mediaPlayer.stop();
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if(position>0 && path!=null){
                        play();
                        mediaPlayer.seekTo(position);
                        position = 0;
                    }
                default:
                    break;
            }
        }
    }

    public void mediaplay(View v){
        switch (v.getId()) {
            case R.id.playButton:
                String fileName = nameText.getText().toString();
                File audio = new File(Environment.getExternalStorageDirectory(),fileName);
                if(audio.exists()){
                    path = audio.getAbsolutePath();
                    play();
                }else{
                    Toast.makeText(getApplicationContext(), fileName + "不存在", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pauseButton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pause = true;
                    ((Button)v).setText(R.string.continues);
                }else{
                    if(pause){
                        mediaPlayer.start();    //继续播放
                        pause = false;
                        ((Button)v).setText(R.string.pauseButton);
                    }
                }
                break;
            case R.id.resetButton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0);  //播发进度置0
                }else{
                    if(path!=null){
                        play();
                    }
                }
                break;
            case R.id.stopButton:
                if(mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                break;
        }
    }
    //播放媒体文件
    private void play() {
        try {
            mediaPlayer.reset();    //重置各项参数
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();  //进行数据缓冲
            mediaPlayer.setOnPreparedListener(new PreparedListener());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //监听缓冲完毕事件
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        //缓冲完毕回调该方法
        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();;   //开始播放
        }

    }
    public void openActivity(View v){
        Intent intent = new Intent(Mp3Activity.this,MyActivity.class);
        startActivity(intent);
    }
}