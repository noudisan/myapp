package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.common.net.NetworkDataParam;
import com.common.task.QueryNetworkDataAsyncTask;
import com.common.util.Constants;
import com.common.util.Logger;
import com.common.util.Utils;

import java.util.HashMap;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public class LoginActivity extends Activity {

    private EditText loginName;
    private EditText loginPassword;
    private Button loginSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getViewFromPage();
        setEvent();
    }

    private void getViewFromPage() {
        loginName = (EditText) findViewById(R.id.login_name);
        loginPassword = (EditText) findViewById(R.id.login_password);

        loginSubmit = (Button) findViewById(R.id.login_submit);
    }

    private void setEvent() {
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String name = loginName.getText().toString();
                    String password = loginPassword.getText().toString();
                    Logger.error("<<<<<<<<<<<<<<<<name:" + name + "\tpassword" + password);
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("userCode", name);
                    params.put("password", password);
                    params.put("version", "1.0.0");
                    params.put("appVersion", Utils.getAppVersionName(LoginActivity.this));
                    NetworkDataParam networkDataParam = new NetworkDataParam(Constants.DeliveryGateWay.ACTION_LOGIN,
                            params);
                    QueryNetworkDataAsyncTask task = new QueryNetworkDataAsyncTask(LoginActivity.this,
                            R.string.message_login_loading, 0, R.string.message_login_fail, taskEndedListener);
                    task.execute(networkDataParam);
                } catch (Exception e) {
                    Logger.uploadError("login error", e);
                }
            }
        });
    }

    private QueryNetworkDataAsyncTask.OnTaskEndedListener taskEndedListener = new QueryNetworkDataAsyncTask.OnTaskEndedListener() {
        @Override
        public void onTaskEnded(int id, int result, Object data) {
            try {
                if (result == Constants.ErrorCode.SUCCESS) {
                    Logger.error(">>>>>>>>>>>>>>>>>>>>>success");
                    Intent i = new Intent(LoginActivity.this, MyActivity.class);
                    startActivity(i);
                }
            } catch (Exception e) {
                Logger.uploadError("login button click error ", e);
            }
        }
    };

}
