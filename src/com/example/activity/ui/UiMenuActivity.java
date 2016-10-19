package com.example.activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.example.activity.R;
import com.example.activity.menu.ActionModeMenu1;
import com.example.activity.menu.ContextMenu1;
import com.example.activity.menu.OptionMenu1Activitty;
import com.example.activity.menu.OptionMenu2Activitty;

public class UiMenuActivity  extends Activity implements View.OnClickListener {

    private Button menu_btn1, menu_btn2, menu_btn3, menu_btn4, menu_btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_menu);

        menu_btn1 =(Button)findViewById(R.id.menu_btn1);
        menu_btn1.setOnClickListener(this);
        menu_btn2 =(Button)findViewById(R.id.menu_btn2);
        menu_btn2.setOnClickListener(this);
        menu_btn3 =(Button)findViewById(R.id.menu_btn3);
        menu_btn3.setOnClickListener(this);
        menu_btn4 =(Button)findViewById(R.id.menu_btn4);
        menu_btn4.setOnClickListener(this);
        menu_btn5 =(Button)findViewById(R.id.menu_btn5);
        menu_btn5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.menu_btn1:
                intent=new Intent(UiMenuActivity.this,OptionMenu1Activitty.class);
                startActivity(intent);
                break;
            case R.id.menu_btn2:
                intent=new Intent(UiMenuActivity.this,OptionMenu2Activitty.class);
                startActivity(intent);
                break;
            case R.id.menu_btn3:
                intent=new Intent(UiMenuActivity.this,ContextMenu1.class);
                startActivity(intent);
                break;
            case R.id.menu_btn4:
                intent=new Intent(UiMenuActivity.this,ActionModeMenu1.class);
                startActivity(intent);
                break;
            case R.id.menu_btn5:
                showPopup(v);
                break;
            default:
                break;
        }

    }

    public void showPopup(View v){
        PopupMenu popup=new PopupMenu(UiMenuActivity.this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_copy:
                        Toast.makeText(UiMenuActivity.this, "select copy ", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.context_delete:
                        Toast.makeText(UiMenuActivity.this, " select delete ", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.context_edit:
                        Toast.makeText(UiMenuActivity.this, " select edit ", Toast.LENGTH_SHORT).show();
                        return true;
                    default :
                        return false;
                }
            }
        });
        popup.getMenuInflater().inflate(R.menu.contextmenu,popup.getMenu());
        popup.show();
    }
}
