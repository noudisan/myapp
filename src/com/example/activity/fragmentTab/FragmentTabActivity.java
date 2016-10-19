package com.example.activity.fragmentTab;

import com.example.activity.fragmentSimple.Fragment1;
import com.example.activity.fragmentSimple.Fragment2;
import com.example.activity.fragmentTurn.Fragment3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.activity.R;

public class FragmentTabActivity extends Activity {
    private TextView tabfgt1, tabfgt2, tabfgt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenttab);

        tabfgt1 = (TextView) findViewById(R.id.tabfgt1);
        tabfgt2 = (TextView) findViewById(R.id.tabfgt2);
        tabfgt3 = (TextView) findViewById(R.id.tabfgt3);

        tabfgt1.setOnClickListener(click);
        tabfgt2.setOnClickListener(click);
        tabfgt3.setOnClickListener(click);

    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tabfgt1.setBackgroundColor(Color.GRAY);
            tabfgt2.setBackgroundColor(Color.GRAY);
            tabfgt3.setBackgroundColor(Color.GRAY);
            // 获取FragmentManager对象
            FragmentManager fm = getFragmentManager();
            // 开启事务
            FragmentTransaction ft = fm.beginTransaction();
            switch (v.getId()) {
                case R.id.tabfgt1:
                    tabfgt1.setBackgroundColor(Color.GREEN);
                    // 替换R.id.content中的Fragment
                    ft.replace(R.id.content, new Fragment1());
                    break;
                case R.id.tabfgt2:
                    tabfgt2.setBackgroundColor(Color.YELLOW);
                    ft.replace(R.id.content, new Fragment2());
                    break;
                case R.id.tabfgt3:
                    tabfgt3.setBackgroundColor(Color.RED);
                    ft.replace(R.id.content, new Fragment3());
                    break;
                default:
                    break;
            }
            // 提交事务
            ft.commit();
        }

    };
}
