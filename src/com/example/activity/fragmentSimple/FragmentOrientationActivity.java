package com.example.activity.fragmentSimple;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import com.example.activity.R;

public class FragmentOrientationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentorien);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        Point point = new Point();
        d.getSize(point);
        if (point.x > point.y) {
            Fragment1 f1 = new Fragment1();
            fragmentTransaction.replace(android.R.id.content, f1);
        } else {
            Fragment2 f2 = new Fragment2();

            fragmentTransaction.replace(android.R.id.content, f2);
        }
        fragmentTransaction.commit();
    }
}
