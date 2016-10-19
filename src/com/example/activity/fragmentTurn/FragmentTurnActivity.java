package com.example.activity.fragmentTurn;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.activity.R;

public class FragmentTurnActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentturn);
        // 方法1：在Activity中操作旗下Fragment中的控件
        Button btn = (Button) findViewById(R.id.btnGetText);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView tv = (TextView) findViewById(R.id.tvFragment1);
                tv.setText("动态修改");
                Toast.makeText(FragmentTurnActivity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
