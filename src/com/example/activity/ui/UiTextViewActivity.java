package com.example.activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.common.app.BaseActivity;
import com.common.dao.DaoFactory;
import com.example.activity.MyActivity;
import com.example.activity.R;
import com.example.dao.LocationDao;
import com.example.model.Location;

import java.lang.reflect.Field;

public class UiTextViewActivity extends BaseActivity {
    private TextView textView_html;
    private TextView textView_link;

    private TextView textView_photo;
    private TextView textView_sliding;

    private TextView textView_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_textview);

        setNavBar();

          /*
         * 实现在TextView中显示URL、不同大小、字体、颜色的文本
         * */
        textView_html = (TextView) findViewById(R.id.textview_html);
        textView_link = (TextView) findViewById(R.id.textview_link);
        textView_photo = (TextView) findViewById(R.id.textview_photo);
        textView_sliding = (TextView) findViewById(R.id.textview_sliding);
        textView_activity = (TextView) findViewById(R.id.textview_sliding);

        saveLocation();

        LocationDao locationDao = (LocationDao) DaoFactory.getInstance(getApplicationContext()).getDAO(
                DaoFactory.TYPE_LOCATION);
        Location result = locationDao.getLastLocation();
        //添加一段html的标示
        String html = "<font color='red'>I love Android</font><br>";
        html += "<font color='#00ff00'><big><i> I love Android </i></big></font><p>";
        html += "<big><a href='http://www.baidu.com'>百度</a></big>" + result.toString();
        CharSequence charSequence = Html.fromHtml(html);
        textView_html.setText(charSequence);
        textView_html.setMovementMethod(LinkMovementMethod.getInstance());

        //点击的时候产生超链接
        String text = "我的URL：http://www.sina.com\n"; //这里的\n是换行符
        text += "我的Email:zhoutaotao0903@163.com\n";
        text += "我的电话：+8618621536559";
        textView_link.setText(text);
        textView_link.setMovementMethod(LinkMovementMethod.getInstance());


        //实现在TextView中显示表情图片和文本
        String html2 = "图像1<img src='icon'>图像2<img src='icon'>图像3<img src='icon3'>图像4" +
                "<a href='http://www.baidu.com'><img src='icon'></a>图像5<img src='icon'>";
        CharSequence charSequence2 = Html.fromHtml(html2, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                //获得系统资源的信息，比如图片信息
                Drawable drawable = getResources().getDrawable(getResourceId(source));
                //第三个图片文件按照50%的比例进行压缩
                if (source.equals("icon3")) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
                } else {//原大小输出
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                }
                return drawable;
            }
        }, null);

        textView_photo.setText(charSequence2);
        textView_photo.setMovementMethod(LinkMovementMethod.getInstance());


        String text4 = "跑马灯跑马灯，这是一个跑马灯！！";
        textView_sliding.setText(text4);
        textView_sliding.setMovementMethod(LinkMovementMethod.getInstance());

        String text5 = "打开Main Activity";
        SpannableString spannableString1 = new SpannableString(text5);
        spannableString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UiTextViewActivity.this, MyActivity.class);
                startActivity(intent);
            }
        }, 0, text5.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_activity.setText(spannableString1);
        textView_activity.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void  setNavBar(){
        setTitleText(R.string.title_idcard_manager);
        setNavBarLeftButton(R.drawable.round_button_empty_selector, R.string.btn_text_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setNavBarRightButton(R.drawable.round_button_empty_selector, R.string.title_clear_community, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* DialogAlertView dialog = new DialogAlertView(MyActivity.this);
                dialog.setTitle(R.string.tv_text_clear_community);
                dialog.setMessage(R.string.tv_text_clear_community_confirm);
                dialog.setOkButton(R.string.btn_text_confirm, new OnDialogButtonClickListener() {

                    @Override
                    public void onClick(DialogAlertView dialog) {
                        CommunityDAO communityDAO = (CommunityDAO) DaoFactory.getInstance(CommunitySelectActivity.this).getDAO(DaoFactory.TYPE_COMMUNITY);
                        communityDAO.deleteCommunity();
                        other_community_layout.removeAllViews();
                    }
                });
                dialog.setCancelButton(R.string.btn_text_cancel, new OnDialogButtonClickListener() {

                    @Override
                    public void onClick(DialogAlertView dialog) {
                        dialog.cancel();
                    }
                });
                dialog.show();*/
            }
        });
    }

    //保存
    private void saveLocation() {
        LocationDao locationDao = (LocationDao) DaoFactory.getInstance(getApplicationContext()).getDAO(
                DaoFactory.TYPE_LOCATION);
        Location location = new Location();
        location.setLoginName("aa");
        location.setLatitude("aa");
        location.setLongitude("aa");
        locationDao.insert(location);

    }

    public int getResourceId(String name) {
        Field field;
        try {
            //根据资源ID的变量名称来获得Field的对象，使用反射机制来实现的
            field = R.drawable.class.getField(name);
            //取得并返回资源的id的字段(静态变量)的值，使用反射机制
            return Integer.parseInt(field.get(null).toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
