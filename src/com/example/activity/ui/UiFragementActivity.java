package com.example.activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.example.activity.R;
import com.example.activity.fragmentSimple.FragmentOrientationActivity;
import com.example.activity.fragmentSimple.FragmentSimple;
import com.example.activity.fragmentTab.FragmentTabActivity;
import com.example.activity.fragmentTurn.FragmentTurnActivity;

/**
 * Created by zhoutaotao on 6/7/15.
 */
public class UiFragementActivity extends Activity {
    private Button btnFragmentSimple, btnFragmentOrien, btnFragmentOfActivity, btnFragmentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_fragmenttab);

        btnFragmentSimple = (Button) findViewById(R.id.btnFragmentSimple);
        btnFragmentOrien = (Button) findViewById(R.id.btnFragmentOrien);
        btnFragmentOfActivity = (Button) findViewById(R.id.btnFragmentOfActivity);
        btnFragmentTab = (Button) findViewById(R.id.btnFragmentTab);

        btnFragmentTab.setOnClickListener(myClick);
        btnFragmentSimple.setOnClickListener(myClick);
        btnFragmentOrien.setOnClickListener(myClick);
        btnFragmentOfActivity.setOnClickListener(myClick);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btnFragmentSimple:
                    intent = new Intent(UiFragementActivity.this, FragmentSimple.class);
                    startActivity(intent);
                    break;
                case R.id.btnFragmentTab:
                    intent = new Intent(UiFragementActivity.this, FragmentTabActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnFragmentOrien:
                    intent = new Intent(UiFragementActivity.this, FragmentOrientationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnFragmentOfActivity:
                    intent = new Intent(UiFragementActivity.this, FragmentTurnActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
