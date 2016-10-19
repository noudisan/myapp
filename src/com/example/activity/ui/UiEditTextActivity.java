package com.example.activity.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.activity.R;

import java.lang.reflect.Field;
import java.util.Random;

public class UiEditTextActivity extends Activity {

    private EditText editText_face;
    private Button button_face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_edittext);

        editText_face = (EditText) findViewById(R.id.editText_face);
        button_face = (Button) findViewById(R.id.button_face);
        button_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomId = 1 + new Random().nextInt(5);//nextInt(9)的范围是0-8
                try {
                    //注：image+randomId为放在 drawable-hdpi中的图片
                    Field field = R.drawable.class.getDeclaredField("image" + randomId);
                    int resourceId = Integer.parseInt(field.get(null).toString());
                    //在Android中要显示图片信息，必须使用BitMap的对象来加载
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                    ImageSpan imageSpan = new ImageSpan(UiEditTextActivity.this, bitmap);
                    SpannableString spannableString = new SpannableString("image"); //“image”是图片名称的前缀
                    spannableString.setSpan(imageSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editText_face.append(spannableString);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        final EditText editText = (EditText) findViewById(R.id.edit_text);
        //监听回车键
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(UiEditTextActivity.this, String.valueOf(actionId), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //获取EditText文本
        Button getValue = (Button) findViewById(R.id.btn_get_value);
        getValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UiEditTextActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //让EditText全选
        Button all = (Button) findViewById(R.id.btn_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.selectAll();
            }
        });
        //从第2个字符开始选择EditText文本
        Button select = (Button) findViewById(R.id.btn_select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = editText.getText();
                Selection.setSelection(editable, 1, editable.length());
            }
        });
        //获取选中的文本
        Button getSelect = (Button) findViewById(R.id.btn_get_select);
        getSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start = editText.getSelectionStart();
                int end = editText.getSelectionEnd();
                CharSequence selectText = editText.getText().subSequence(start, end);
                Toast.makeText(UiEditTextActivity.this, selectText, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 交换两个索引
     *
     * @param start 开始索引
     * @param end   结束索引
     */
    protected void switchIndex(int start, int end) {
        int temp = start;
        start = end;
        end = temp;
    }
}
