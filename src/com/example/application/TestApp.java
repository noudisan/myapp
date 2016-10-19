package com.example.application;

import android.app.Application;
import com.baidu.mapapi.SDKInitializer;
import com.example.handler.CrashHandler;

/**
 * Created by zhoutaotao on 6/17/15.
 */
public class TestApp extends Application {

    private static TestApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);

        sInstance = this;

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public static TestApp getInstance() {
        return sInstance;
    }

}
