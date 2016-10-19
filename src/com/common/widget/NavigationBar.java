package com.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.activity.R;

public class NavigationBar extends LinearLayout {
	private RelativeLayout layoutBackground;
	private View layout;
	private TextView mTextViewTitle;
	private Button mButtonLeft;
	private Button mButtonRight;
	public NavigationBar(Context context) {
		this(context, null);
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		onCreateView(this);
	}

	public void setBackground(int id) {
		layoutBackground.setBackgroundResource(id);
		mTextViewTitle.setTextColor(Color.WHITE);
		invalidate();
	}

	private void onCreateView(ViewGroup parent) {
		final LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = layoutInflater.inflate(R.layout.zuomj_navigation_bar, parent, true);
		layoutBackground = (RelativeLayout) layout.findViewById(R.id.layout_background);
		mTextViewTitle = (TextView) layout.findViewById(R.id.textview_title);

		mButtonLeft = (Button) layout.findViewById(R.id.button_left);
		mButtonRight = (Button) layout.findViewById(R.id.button_right);
	}

	public void setTitleText(int titleResId) {
		mTextViewTitle.setText(titleResId);
	}

	public void setTitleText(CharSequence title) {
		mTextViewTitle.setText(title);
	}

	public void setTitleTextWithClick(CharSequence title,OnClickListener listener) {
		mTextViewTitle.setText(title);
		mTextViewTitle.setOnClickListener(listener);

		Drawable drawable= getResources().getDrawable(R.drawable.icon_home_community);  
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
		mTextViewTitle.setCompoundDrawables(null,null,drawable,null);  
		mTextViewTitle.setCompoundDrawablePadding(turnDipToPx(2, getContext()));//??
	}

	public static int turnDipToPx(int dip, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	public CharSequence getTitleText() {
		return mTextViewTitle.getText();
	}

	public void hideButton() {
		mButtonLeft.setVisibility(View.GONE);
		mButtonRight.setVisibility(View.GONE);
	}

	public void setLeftButton(int imgResId, int textResId, OnClickListener listener) {
		mButtonLeft.setBackgroundResource(imgResId);
		if (textResId > 0) {
			mButtonLeft.setText(textResId);
		}
		mButtonLeft.setOnClickListener(listener);
		mButtonLeft.setVisibility(View.VISIBLE);
	}

	public void setRightButton(int imgResId, int textResId, OnClickListener listener) {
		mButtonRight.setBackgroundResource(imgResId);
		if (textResId > 0) {
			mButtonRight.setText(textResId);
		}
		mButtonRight.setOnClickListener(listener);
		mButtonRight.setVisibility(View.VISIBLE);
	}
	
	public void setRightButtonText(int textResId){
		if (textResId > 0) {
			mButtonRight.setText(textResId);
		}
	}
	public void setNavBarGone(){
		layout.setVisibility(View.GONE);
	}
}
