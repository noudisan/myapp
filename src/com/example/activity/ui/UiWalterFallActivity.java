package com.example.activity.ui;


import android.app.Activity;
import android.os.Bundle;
import com.example.activity.R;
import com.example.activity.ui.walterfall.WaterFall;

public class UiWalterFallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_walterfall);

        WaterFall waterFall = (WaterFall) findViewById(R.id.waterfall);
        waterFall.setup();
    }

}

