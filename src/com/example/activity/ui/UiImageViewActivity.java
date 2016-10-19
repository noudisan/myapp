package com.example.activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.example.activity.R;

import java.io.FileNotFoundException;


public class UiImageViewActivity extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private int minWidth = 80;
    private ImageView imageView1;
    private SeekBar seekBar_zoom; //缩放
    private SeekBar seekBar_rotate; //旋转
    private Matrix matrix = new Matrix();

    private Button imageSelectBtn;
    private Button imageCutBtn;
    private ImageView imageViewShow;
    // 声明两个静态整型变量，用于意图的返回标志
    private static final int IMAGE_SELECT = 1; // 选择图片
    private static final int IMAGE_CUT = 2; // 裁剪图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_imageview);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        seekBar_zoom = (SeekBar) findViewById(R.id.seekBar_zoom);
        seekBar_zoom.setOnSeekBarChangeListener(this);

        seekBar_rotate = (SeekBar) findViewById(R.id.seekBar_rotate);
        seekBar_rotate.setOnSeekBarChangeListener(this);

        // 定义一个DisplayMetrics对象，用来显示旋转的图像
        DisplayMetrics dm = new DisplayMetrics();
        // 根据手机屏幕大小来缩放
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        seekBar_zoom.setMax(dm.widthPixels - minWidth);

        setupViews();
    }

    // 推荐作风
    public void setupViews() {
        imageSelectBtn = (Button) findViewById(R.id.imageSelectButton);
        imageSelectBtn.setOnClickListener(this);
        imageCutBtn = (Button) findViewById(R.id.imageCutButton);
        imageCutBtn.setOnClickListener(this);
        imageViewShow = (ImageView) findViewById(R.id.imageViewShow);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar_zoom:
                int newWidth = progress + minWidth;
                int newHeight = (int) (newWidth * 3 / 4);
                imageView1.setLayoutParams(new LinearLayout.LayoutParams(newWidth, newHeight));
                break;
            case R.id.seekBar_rotate:
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.pic)).getBitmap();
                // 设置旋转角度
                matrix.setRotate(progress);
                // 重新绘制Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView1.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageSelectButton:
                // 如何提取手机的图片，并且进行图片的选择
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_SELECT);
                break;
            case R.id.imageCutButton:
                Intent intent2 = getImageClipIntent();
                startActivityForResult(intent2, IMAGE_CUT);
                break;
            default:
                break;
        }
    }

    // 获取裁剪图片意图的方法
    private Intent getImageClipIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        // 实现对图片的裁剪,必须要设置图片的属性和大小
        intent.setType("image/*"); // 设置属性，表示获取任意类型的图片
        intent.putExtra("crop", "true");// 设置可以滑动选选择区域的属性,注意这里是字符串"true"
        intent.putExtra("aspectX", 1);// 设置剪切框1:1比例的效果
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 处理图片按照手机屏幕大小显示
            if (requestCode == IMAGE_SELECT) {
                // 获得图片的路径
                Uri uri = data.getData();
                // 获得屏幕宽度
                int dw = getWindowManager().getDefaultDisplay().getWidth();
                // 获得屏幕宽度
                int dh = getWindowManager().getDefaultDisplay().getHeight() / 2;
                try {
                    // 实现对图片裁剪的类，是一个匿名内部类
                    BitmapFactory.Options factory = new BitmapFactory.Options();
                    // 如果设置为true,允许查询图片不是按照像素分配内存
                    factory.inJustDecodeBounds = true;
                    Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
                    // 对图片的高度和宽度对应手机屏幕进行匹配
                    // 宽度之比
                    int wRatio = (int) Math.ceil(factory.outWidth / (float) dw);
                    // 高度之比
                    int hRatio = (int) Math.ceil(factory.outHeight / (float) dh);
                    // 如果wRatio大于1，表示图片的宽度大于屏幕宽度,类似hRatio
                    if (wRatio > 1 || hRatio > 1) {
                        // inSampleSize>1则返回比原图更小的图片
                        if (hRatio > wRatio) {
                            factory.inSampleSize = hRatio;
                        } else {
                            factory.inSampleSize = wRatio;
                        }
                    }
                    // 该属性为false则允许调用者查询图片无需为像素分配内存
                    factory.inJustDecodeBounds = false;
                    // 再次使用BitmapFactory对象图像进行适屏操作
                    bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
                    imageViewShow.setImageBitmap(bmp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == IMAGE_CUT) { // 裁剪图片
                // 一定要和"return-data"返回的标签"data"一致
                Bitmap bmp = data.getParcelableExtra("data");
                imageViewShow.setImageBitmap(bmp);
            }
        }
    }

}
