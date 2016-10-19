package com.example.activity.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.activity.R;

public class IntentActivity extends Activity {
	private TextView tvOptionmenu1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_optionmenu1);

		tvOptionmenu1=(TextView)findViewById(R.id.tvOptionMenu1);
		tvOptionmenu1.setText("使用Intent跳转");
	}
}
