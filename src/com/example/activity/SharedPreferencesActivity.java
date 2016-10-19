package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.shared.MySharedPreferences;

import java.util.List;

public class SharedPreferencesActivity extends Activity {
    private EditText etKey, etValue;
    private Button btnPut, btnGet, btnQuery, btnRemove;
    private ListView lvAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreferences);
        etKey = (EditText) findViewById(R.id.etSharedKey);
        etValue = (EditText) findViewById(R.id.etSharedValue);
        btnPut = (Button) findViewById(R.id.btnSharedPut);
        btnGet = (Button) findViewById(R.id.btnSharedGet);
        btnQuery = (Button) findViewById(R.id.btnSharedQuery);
        btnRemove = (Button) findViewById(R.id.btnSharedRemove);
        lvAll = (ListView) findViewById(R.id.lvSharedData);

        lvAll.setOnItemClickListener(itemClick);

        btnPut.setOnClickListener(click);
        btnGet.setOnClickListener(click);
        btnQuery.setOnClickListener(click);
        btnRemove.setOnClickListener(click);
    }

    private OnItemClickListener itemClick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ListView lv = (ListView) parent;
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) lv.getAdapter();
            String str = adapter.getItem(position);
            int index = str.indexOf("-");
            String Key = str.substring(4, index);
            String value = str.substring(index + 7, str.length());
            etKey.setText(Key);
            etValue.setText(value);

        }
    };
    private OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View v) {
            MySharedPreferences myShared = null;
            String key = null;
            String value = null;
            switch (v.getId()) {
                case R.id.btnSharedPut:
                    myShared = new MySharedPreferences(SharedPreferencesActivity.this);
                    key = etKey.getText().toString();
                    value = etValue.getText().toString();
                    myShared.putData(key, value);
                    Toast.makeText(SharedPreferencesActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.btnSharedGet:

                    key = etKey.getText().toString();
                    if (key != null && !key.equals("")) {
                        myShared = new MySharedPreferences(SharedPreferencesActivity.this);
                        value = myShared.getValue(key);
                        etValue.setText(value);
                        Toast.makeText(SharedPreferencesActivity.this, "查询数据成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnSharedQuery:
                    myShared = new MySharedPreferences(SharedPreferencesActivity.this);
                    List<String> SharedAll = myShared.queryAllKey();
                    lvAll.setAdapter(new ArrayAdapter<String>(SharedPreferencesActivity.this, android.R.layout.simple_list_item_1, SharedAll));
                    break;
                case R.id.btnSharedRemove:
                    key = etKey.getText().toString();
                    if (key != null && !key.equals("")) {
                        myShared = new MySharedPreferences(SharedPreferencesActivity.this);
                        if (myShared.removeData(key)) {
                            Toast.makeText(SharedPreferencesActivity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SharedPreferencesActivity.this, "删除数据失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;
            }

        }
    };

}
