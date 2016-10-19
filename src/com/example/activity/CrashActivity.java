package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 异常crash 处理 ->TestApp
 */
public class CrashActivity extends Activity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        initView();
    }

    private void initView() {
        mButton = (Button)findViewById(R.id.crash_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mButton) {
            //在这里默认异常抛出情况，人为抛出一个运行时异常
            throw new RuntimeException("自定义异常：这是自己抛出的异常");
        }

    }
}
