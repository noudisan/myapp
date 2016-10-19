package com.common.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.common.widget.NavigationBar;
import com.example.activity.R;

public class BaseActivity extends Activity {

    private LinearLayout rootLayout;
    private LinearLayout contentLayout;
    protected NavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootLayout = (LinearLayout) LayoutInflater.from(BaseActivity.this).inflate(R.layout.zuomj_activity_rootlayout, null);
        super.setContentView(rootLayout);

        navigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
    }
    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        contentLayout.addView(view, params);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        contentLayout.addView(view, params);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        contentLayout.addView(view, params);
        // mHelper.registerAboveContentView(view, params);
    }

    public void setContentLayoutBackground(int id) {

        rootLayout.setBackgroundResource(id);
    }

    protected void setTitleText(int titleResId) {
        navigationBar.setTitleText(titleResId);
    }

    protected void setTitleText(CharSequence title) {
        navigationBar.setTitleText(title);
    }

    protected void setTitleTextWithClick(CharSequence title, View.OnClickListener listener) {
        navigationBar.setTitleTextWithClick(title, listener);
    }

    protected void hideNavigationBar() {
        navigationBar.setVisibility(View.GONE);
    }

    protected void showNavigationBar() {
        navigationBar.setVisibility(View.VISIBLE);
    }

    protected void setNavBarLeftButton(int imgResId, int textResId, View.OnClickListener listener) {
        navigationBar.setLeftButton(imgResId, textResId, listener);
    }

    public void setNavBarRightButton(int imgResId, int textResId, View.OnClickListener listener) {
        navigationBar.setRightButton(imgResId, textResId, listener);
    }

    protected void setNavBarRightButtonText(int textResId) {
        navigationBar.setRightButtonText(textResId);
    }

    protected void setNavBarGone() {
        navigationBar.setNavBarGone();
    }



    @Override
    public void finish() {
//        GlobalApplication application = (GlobalApplication) getApplication();
//        ScreenManager screenManager = application.getScreenManager(groupKey);
//        screenManager.removeActivity(this);
        super.finish();

//        if (screenManager.activityCount() > 0) {
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        } else {
//            overridePendingTransition(R.anim.anim_static, R.anim.slide_out_bottom);
//        }
    }


}
