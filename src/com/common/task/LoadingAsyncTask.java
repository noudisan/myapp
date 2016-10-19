package com.common.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public abstract class LoadingAsyncTask<Input, Result> extends AsyncTask<Input, String, Result> {
    protected Context context;
    protected int loadingMsg;
    protected int cancelMsg;
    protected ProgressDialog progressDialog;

    public LoadingAsyncTask(Context context, int loadingMsg, int cancelMsg) {
        this.context = context;
        this.loadingMsg = loadingMsg;
        this.cancelMsg = cancelMsg;
    }

    public void onCancelled() {
        super.onCancelled();
        if (cancelMsg != 0) {
            Toast.makeText(context, cancelMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();

        String title = "";
        if (loadingMsg != 0) {
            String message = context.getString(loadingMsg);
            try {
                progressDialog = ProgressDialog.show(context, title, message, true, true, new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        cancel(true);
                    }
                });
                progressDialog.setCanceledOnTouchOutside(false);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public abstract Result doInBackground(Input... params);

    public void onPostExecute(Result result) {
        super.onPostExecute(result);
        doStuffWithResult(result);
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.cancel();
            } catch (Exception e) {
            }
        }
    }

    public abstract void doStuffWithResult(Result result);

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        cancel(true);
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.cancel();
            } catch (Exception e) {
            }
        }
    }
}
