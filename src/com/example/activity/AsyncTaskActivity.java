package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskActivity extends Activity {
    private ImageView image = null;
    private Button show;
    private ProgressBar progressBar = null;
    private int number = 0;
    List<String> imageUrl = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_task);

        progressBar = (ProgressBar) findViewById(R.id.processBar);
        image = (ImageView) findViewById(R.id.image);
        show = (Button) findViewById(R.id.show);

        show.setOnClickListener(new showButtonListener());

        imageUrl = new ArrayList<String>(); // 图片地址List
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2011/266/AIO90AV2508S.jpg");
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2012/090/063N2L5N2HID.jpg");
        imageUrl.add("http://comic.sinaimg.cn/2011/0824/U5237P1157DT20110824161051.jpg");
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2012/090/1429QO6389U8.jpg");
        imageUrl.add("http://new.aliyiyao.com/UpFiles/Image/2011/01/13/nc_129393721364387442.jpg");

    }

    public class showButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            number++;
            MyAsyncTask myAsyncTask = new MyAsyncTask(AsyncTaskActivity.this);
            myAsyncTask.execute(imageUrl.get(number % 5));
            System.out.println("url:" + imageUrl.get(number % 5));
        }
    }

    class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        // 可变长的输入参数，与AsyncTask.exucute()对应
        public MyAsyncTask(Context context) {
            progressBar.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                //new URL对象  把网址传入
                URL url = new URL(params[0]);
                //取得链接
                URLConnection conn = url.openConnection();
                conn.connect();
                //取得返回的InputStream
                InputStream inputStream = conn.getInputStream();
                //将InputStream变为Bitmap
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (image == null) {
                    Toast.makeText(getApplicationContext(), "下载出现异常", Toast.LENGTH_SHORT).show();
                }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        /**
         * 在doInBackground 执行完成后，onPostExecute方法将被UI thread调用，后台的计算结果将通过该方法传递到UI thread.
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            if (bitmap != null) {
                image.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 该方法将在执行实际的后台操作前被UI thread调用。这个方法只是做一些准备工作，如在界面上显示一个进度条。
         */
        @Override
        protected void onPreExecute() {
            // 任务启动
            Toast.makeText(getApplicationContext(), "任务开始......", Toast.LENGTH_SHORT).show();
        }
    }
}