package com.example.activity.indicator;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.activity.R;

/**
 * 适配器
 */
public class SampleFragmentPagerAdapter  extends FragmentPagerAdapter{
    private static final String[] CONTENT = new String[]{"A", "B", "C", "D"};

    private Context mContext;

    public SampleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return SampleFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String[]{mContext.getString(R.string.tab1_text), mContext.getString(R.string.tab2_text),
                mContext.getString(R.string.tab3_text), mContext.getString(R.string.tab4_text)}[position];
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }
}
