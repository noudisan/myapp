package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.storage.MyInternalStorage;

import java.io.IOException;

public class InternalStorageActivity extends Activity {
    private EditText etFilename, etContent;
    private Button btnSave, btnQuery, btnDelete, btnAppend;
    private ListView lvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internalstorage);

        etFilename = (EditText) findViewById(R.id.etInternalFilename);
        etContent = (EditText) findViewById(R.id.etInternalContent);
        btnSave = (Button) findViewById(R.id.btnInternalSave);
        btnQuery = (Button) findViewById(R.id.btnInternalQuery);
        btnDelete = (Button) findViewById(R.id.btnInternalDelete);
        btnAppend = (Button) findViewById(R.id.btnInternalAppend);
        lvData = (ListView) findViewById(R.id.lvInternalData);

        btnSave.setOnClickListener(click);
        btnQuery.setOnClickListener(click);
        btnDelete.setOnClickListener(click);
        btnAppend.setOnClickListener(click);

    }

    private OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View v) {
            MyInternalStorage myInternal = null;
            String filename = null;
            String content = null;
            switch (v.getId()) {
                case R.id.btnInternalSave:
                    filename = etFilename.getText().toString();
                    content = etContent.getText().toString();
                    myInternal = new MyInternalStorage(InternalStorageActivity.this);
                    try {
                        myInternal.save(filename, content);
                        Toast.makeText(InternalStorageActivity.this, "保存文件成功",
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(InternalStorageActivity.this, "保存文件失败",
                                Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.btnInternalDelete:
                    filename = etFilename.getText().toString();
                    myInternal = new MyInternalStorage(InternalStorageActivity.this);
                    myInternal.delete(filename);
                    Toast.makeText(InternalStorageActivity.this, "删除文件成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnInternalQuery:
                    myInternal = new MyInternalStorage(InternalStorageActivity.this);
                    String[] files = myInternal.queryAllFile();
                    ArrayAdapter<String> fileArray = new ArrayAdapter<String>(
                            InternalStorageActivity.this,
                            android.R.layout.simple_list_item_1, files);
                    lvData.setAdapter(fileArray);
                    Toast.makeText(InternalStorageActivity.this, "查询文件列表",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnInternalAppend:
                    filename = etFilename.getText().toString();
                    content = etContent.getText().toString();
                    myInternal = new MyInternalStorage(InternalStorageActivity.this);
                    try {
                        myInternal.append(filename, content);
                        Toast.makeText(InternalStorageActivity.this, "文件内容追加成功",
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(InternalStorageActivity.this, "文件内容追加失败",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    private OnItemClickListener itemClick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ListView lv = (ListView) parent;
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) lv
                    .getAdapter();
            String filename = adapter.getItem(position);
            etFilename.setText(filename);
            MyInternalStorage myInternal = new MyInternalStorage(
                    InternalStorageActivity.this);
            String content;
            try {
                content = myInternal.get(filename);
                etContent.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

}
