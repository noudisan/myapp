package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.activity.use.*;

public class MyActivity extends Activity {
    /**
     * 参数设置
     */
    Button startServiceButton;// 启动服务按钮
    Button shutDownServiceButton;// 关闭服务按钮
    Button startBindServiceButton;// 启动绑定服务按钮
    Button sendBroadcast;// 使用广播
    Button notificationButton;// 使用通知功能
    Button alarmButton;// 使用闹钟
    Button handlerButton;// 使用handler
    Button asyncButton;// 使用异步加载
    Button phoneStateButton;// 查看手机状态
    Button callphoneButton;// 拨打电话
    Button vibratorButton;// 使用震动
    Button uiDmoButton;// uiDmo

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MyActivity", "setContentView");
        setContentView(R.layout.main);//设置显示布局文件全部组件
        getWidget();
        regiestListener();
    }
    /**
     * 获得组件
     */
    public void getWidget() {
        startServiceButton = (Button) findViewById(R.id.startServerButton);
        startBindServiceButton = (Button) findViewById(R.id.startBindServerButton);
        shutDownServiceButton = (Button) findViewById(R.id.sutdownServerButton);
        sendBroadcast = (Button) findViewById(R.id.sendBroadcast);
        notificationButton = (Button) findViewById(R.id.notification);
        alarmButton = (Button) findViewById(R.id.alarm);
        handlerButton = (Button) findViewById(R.id.handler);
        asyncButton = (Button) findViewById(R.id.async);
        phoneStateButton = (Button) findViewById(R.id.phonestate);
        callphoneButton = (Button) findViewById(R.id.callphone);
        vibratorButton = (Button) findViewById(R.id.vibrator);
        uiDmoButton = (Button) findViewById(R.id.uiDmo);
    }

    /**
     * 为按钮添加监听
     */
    public void regiestListener() {
        startServiceButton.setOnClickListener(startService);
        shutDownServiceButton.setOnClickListener(shutdownService);
        startBindServiceButton.setOnClickListener(startBinderService);
        sendBroadcast.setOnClickListener(broadcastReceiver);
        notificationButton.setOnClickListener(notification);
        alarmButton.setOnClickListener(startAlarm);
        handlerButton.setOnClickListener(handler);
        asyncButton.setOnClickListener(async);
        phoneStateButton.setOnClickListener(phonestate);
        callphoneButton.setOnClickListener(callphoneEvent);
        vibratorButton.setOnClickListener(vibrator);
        uiDmoButton.setOnClickListener(uiDmo);
    }

    /**
     * 启动服务的事件监听
     */
    public Button.OnClickListener startService = new Button.OnClickListener() {
        public void onClick(View view) {
            /**单击按钮时启动服务*/
            Intent intent = new Intent(MyActivity.this, CountService.class);
            startService(intent);
            Log.v("MyActivity", "start Service");
        }
    };
    /**
     * 关闭服务
     */
    public Button.OnClickListener shutdownService = new Button.OnClickListener() {
        public void onClick(View view) {
            /**单击按钮时启动服务*/
            Intent intent = new Intent(MyActivity.this, CountService.class);
            /**退出Activity是，停止服务*/
            stopService(intent);
            Log.v("MyActivity", "shutDown serveice");
        }
    };
    /**
     * 打开绑定服务的Activity
     */
    public Button.OnClickListener startBinderService = new Button.OnClickListener() {
        public void onClick(View view) {
            /**单击按钮时启动服务*/
            Intent intent = new Intent(MyActivity.this, UseBrider.class);
            startActivity(intent);
            Log.v("MyActivity", "start Binder Service");
        }
    };
    /**
     * 打开广播学习的按钮
     */
    public Button.OnClickListener broadcastReceiver = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseBroadcast.class);
            startActivity(intent);
            Log.v("MyActivity", "start broadcast");
        }
    };
    /**
     * 打开通知
     */
    public Button.OnClickListener notification = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseNotification.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start Notification");

        }
    };
    /**
     * 使用闹钟
     */
    public Button.OnClickListener startAlarm = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseAlarmManager.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start alarm");

        }
    };
    /**
     * 进度条
     */
    public Button.OnClickListener handler = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseHandleMessage.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start handle");
        }
    };
    /**
     * 异步任务
     */
    public Button.OnClickListener async = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseAsyncTask.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start handle");
        }
    };
    /**
     * 用户手机状态
     */
    public Button.OnClickListener phonestate = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UsePhoneState.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start phonestate");
        }
    };
    /**
     *拨打电话
     */
    public Button.OnClickListener callphoneEvent = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseActionCall.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start callphone");
        }
    };
    /**
     * 震动提示
     */
    public Button.OnClickListener vibrator = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UseVibrator.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start callphone");
        }
    };
    /**
     *  demo
     */
    public Button.OnClickListener uiDmo = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MyActivity.this, UiActivity.class);
            startActivity(intent);
            Log.v("MainStadyService ", "start UiActivity");
        }
    };

    /***/
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MyActivity.this, CountService.class);
        /**退出Activity是，停止服务*/
        stopService(intent);
    }

}
