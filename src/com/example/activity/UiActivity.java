package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.activity.map.BaseMapDemo;
import com.example.activity.map.LocationDemo;
import com.example.activity.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UiActivity extends Activity {
    private GridView mGridView;   //MyGridView
    //定义图标数组
    private int[] imageRes = {R.drawable.png1, R.drawable.png1,
            R.drawable.png1, R.drawable.png1, R.drawable.png1, R.drawable.png1,
            R.drawable.png1, R.drawable.png1, R.drawable.png1,R.drawable.png1,
            R.drawable.png1, R.drawable.png1, R.drawable.png1,R.drawable.png1,
            R.drawable.png1, R.drawable.png1, R.drawable.png1, R.drawable.png1,
            R.drawable.png1, R.drawable.png1, R.drawable.png1, R.drawable.png1};
    //定义标题数组
    private String[] itemName = {"TextVeiw", "EditText",
            "ImageView", "ListView", "Gallery","loginDemo",
            "asyncTask", "fragment", "FileDemo", "menuDemo",
            "canvasDemo", "selfDesign","crashDemo", "mapDemo",
            "mapLocation", "about", "WebView", "walterFall",
            "custom", "aaaa", "aaaa", "aaaa"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Activity标题不显示
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏显示
        setContentView(R.layout.ui_nine);//???

        mGridView = (GridView) findViewById(R.id.MyGridView);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int length = itemName.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImageView", imageRes[i]);
            map.put("ItemTextView", itemName[i]);
            data.add(map);
        }
        //为itme.xml添加适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                data, R.layout.items, new String[]{"ItemImageView", "ItemTextView"},
                new int[]{R.id.ItemImageView, R.id.ItemTextView});
        mGridView.setAdapter(simpleAdapter);
        //为mGridView添加点击事件监听器
        mGridView.setOnItemClickListener(new GridViewItemOnClick());
    }

    //定义点击事件监听器
    public class GridViewItemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

            Log.v("UiActivity", "-----------------------");
            if (0 == position) {
                Log.v("UiActivity", "0");
                Intent intent = new Intent(UiActivity.this, UiTextViewActivity.class);
                startActivity(intent);
            } else if (1 == position) {
                Intent intent = new Intent(UiActivity.this, UiEditTextActivity.class);
                startActivity(intent);
            } else if (2 == position) {
                Intent intent = new Intent(UiActivity.this, UiImageViewActivity.class);
                startActivity(intent);
            } else if (3 == position) {
                Intent intent = new Intent(UiActivity.this, UiListViewActivity.class);
                startActivity(intent);
            } else if (4 == position) {
                Intent intent = new Intent(UiActivity.this, UiGalleryActivity.class);
                startActivity(intent);
            } else if (5 == position) {
                Intent intent = new Intent(UiActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (6 == position) {
                Intent intent = new Intent(UiActivity.this, AsyncTaskActivity.class);
                startActivity(intent);
            } else if (7 == position) {
                Intent intent = new Intent(UiActivity.this, UiFragementActivity.class);
                startActivity(intent);
            } else if (8 == position) {
                Intent intent = new Intent(UiActivity.this, FileActivity.class);
                startActivity(intent);
            } else if (9 == position) {
                Intent intent = new Intent(UiActivity.this, UiMenuActivity.class);
                startActivity(intent);
            } else if (10 == position) {
                Intent intent = new Intent(UiActivity.this, CanvasActivity.class);
                startActivity(intent);
            } else if (11 == position) {
                Intent intent = new Intent(UiActivity.this, UiSelfView.class);
                startActivity(intent);
            } else if (12 == position) {
                Intent intent = new Intent(UiActivity.this, CrashActivity.class);
                startActivity(intent);
            }  else if (13 == position) {
                Intent intent = new Intent(UiActivity.this, BaseMapDemo.class);
                startActivity(intent);
            }  else if (14 == position) {
                Intent intent = new Intent(UiActivity.this, LocationDemo.class);
                startActivity(intent);
            } else if (15 == position) {
                Intent intent = new Intent(UiActivity.this, AboutActivity.class);
                startActivity(intent);
            } else if (16 == position) {
                Intent intent = new Intent(UiActivity.this, WebViewActivity.class);
                startActivity(intent);
            } else if (17 == position) {
                Intent intent = new Intent(UiActivity.this, UiWalterFallActivity.class);
                startActivity(intent);
            }else if (18 == position) {
                Intent intent = new Intent(UiActivity.this, CustomViewActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
