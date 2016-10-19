package com.example.activity.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.ActionMode.Callback;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.activity.R;

import java.util.ArrayList;
import java.util.List;

public class ActionModeMenu1 extends Activity {
    private ListView listview;
    private List<String> dataList;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contextmenu1);
        listview = (ListView) findViewById(R.id.listView1);
        dataList = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActionModeMenu1.this, android.R.layout.simple_list_item_1, dataList);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                //显示ActionMode
                mActionMode = startActionMode(mActionModeCallback);
                //标记选中项的下表
                mActionMode.setTag(position);
                //标记ListView为可选状态
                view.setSelected(true);
                return true;
            }
        });
    }

    public List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            data.add("item" + i);
        }
        return data;
    }

    private Callback mActionModeCallback = new Callback() {

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            //销毁ActionMode
            mActionMode = null;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //创建ActionMode
            //使用资源文件填充
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextmenu, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int position = (Integer) mode.getTag();
            switch (item.getItemId()) {
                case R.id.context_copy:
                    Toast.makeText(ActionModeMenu1.this, "copy " + dataList.get(position), Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.context_delete:
                    Toast.makeText(ActionModeMenu1.this, "delete " + dataList.get(position), Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.context_edit:
                    Toast.makeText(ActionModeMenu1.this, "edit " + dataList.get(position), Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }
    };


}
