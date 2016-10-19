package com.example.activity.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import com.example.activity.R;


public class UiGalleryActivity extends Activity {
    private Gallery gallery;
    private ImageView imageViewGallery;
    private int[] imgs = {R.drawable.pic, R.drawable.pic, R.drawable.pic, R.drawable.pic
            , R.drawable.pic, R.drawable.pic, R.drawable.pic, R.drawable.pic, R.drawable.pic};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_gallery);

        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        gallery = (Gallery) findViewById(R.id.gallery);

        MyImgAdapter adapter = new MyImgAdapter(this);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View arg1, int position, long arg3) {
                imageViewGallery.setImageResource(imgs[position]);
            }

        });
    }

    public class MyImgAdapter extends BaseAdapter {
        private Context context;//用于接收传递过来的Context对象

        public MyImgAdapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public Object getItem(int position) {
            return imgs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //针对每一个数据（即每一个图片ID）创建一个ImageView实例，
            ImageView iv = new ImageView(UiGalleryActivity.this);//针对外面传递过来的Context变量，
            iv.setImageResource(imgs[position]);
            iv.setLayoutParams(new Gallery.LayoutParams(160, 160));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }

    }
}