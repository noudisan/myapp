package com.example.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.example.activity.indicator.LineTabIndicator;
import com.example.activity.indicator.SampleFragmentPagerAdapter;

public class LineTabActivity extends FragmentActivity {
    private LineTabIndicator lineTabIndicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_line_tab);

        lineTabIndicator = (LineTabIndicator) findViewById(R.id.line_tab_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(this, getSupportFragmentManager()));
        lineTabIndicator.setViewPager(viewPager);
    }
}
