package com.example.activity.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.activity.R;
import com.example.data.MyData;

import java.util.ArrayList;
import java.util.HashMap;


public class UiListViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_listview);

        ListView listView = (ListView) findViewById(R.id.MyListView);
        //生成适配器，数组
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 16; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Resources res = getResources();
            //将Drawable图片资源转化为Bitmap对象
            Bitmap bmp = BitmapFactory.decodeResource(res, MyData.images[i]);
            map.put("image", bmp);
            map.put("name", MyData.names[i]);
            map.put("time", MyData.times[i]);
            map.put("content", MyData.contents[i]);
            dataList.add(map);
        }
        //生成适配器，数组
        SimpleAdapter simpleAdapter = new SimpleAdapter(UiListViewActivity.this, dataList, R.layout.ui_listview_items,
                new String[]{"image", "name", "time", "content"},
                new int[]{R.id.image, R.id.name, R.id.time, R.id.content});

        //绑定数据到listView
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                //判断是否为我们要处理的对象
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                } else
                    return false;
            }
        });

        listView.setAdapter(simpleAdapter);
    }

}
