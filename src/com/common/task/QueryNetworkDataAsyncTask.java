package com.common.task;

import android.content.Context;
import android.text.TextUtils;
import com.common.config.UserConfig;
import com.common.net.Caller;
import com.common.net.NetworkDataParam;
import com.common.parse.JsonParseProxy;
import com.common.parse.LoginParse;
import com.common.util.Constants;
import com.common.util.Result;
import com.common.util.Utils;

import com.common.parse.IJsonParse;
import com.common.util.Logger;
import com.example.activity.R;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public class QueryNetworkDataAsyncTask extends LoadingAsyncTask<NetworkDataParam, Result> {
    public interface OnTaskEndedListener {
        void onTaskEnded(int id, int result, Object data);
    }

    private OnTaskEndedListener listener;
    private int successMsg;
    private int failMsg;
    private IJsonParse defaultParse;

    private int id;

    public QueryNetworkDataAsyncTask(Context context, int loadingMsg, int successMsg, int failMsg) {
        this(context, loadingMsg, successMsg, failMsg, null);
    }

    public QueryNetworkDataAsyncTask(Context context, int loadingMsg, int successMsg, int failMsg, OnTaskEndedListener listener) {
        this(context, loadingMsg, R.string.message_task_cancel, successMsg, failMsg, listener);
    }

    public QueryNetworkDataAsyncTask(Context context, int loadingMsg, int cancelMsg, int successMsg, int failMsg, OnTaskEndedListener listener) {
        super(context, loadingMsg, cancelMsg);

        this.successMsg = successMsg;
        this.failMsg = failMsg;
        this.listener = listener;
    }

    public void setDefaultParse(IJsonParse defaultParse) {
        this.defaultParse = defaultParse;
    }

    @Override
    public void onCancelled() {
        super.onCancelled();

        if (listener != null) {
            listener.onTaskEnded(id, Constants.ErrorCode.OPERATION_CANCELLED, null);
        }
    }

    @Override
    public Result doInBackground(NetworkDataParam... params) {
        NetworkDataParam queryNetworkDataParam = params[0];

        int code;
        try {
            String url = queryNetworkDataParam.getUrl();


            // 添加deviceId字段
            if (null == queryNetworkDataParam.getParams()) {
                queryNetworkDataParam.setParams(new HashMap<String, Object>());
            }
            queryNetworkDataParam.getParams().put("deviceId", getUserConfig().getDeviceId());

            String responseText = Caller.doPost(url, queryNetworkDataParam.getParams(), queryNetworkDataParam.getFiles());
            IJsonParse jsonParse = null;
            if (defaultParse != null) {
                jsonParse = defaultParse;
            }

            if (jsonParse == null) {
                if (queryNetworkDataParam.getUrl().equals(Constants.DeliveryGateWay.ACTION_LOGIN)) {
                    jsonParse = new LoginParse();
                }
            }

            return new JsonParseProxy(jsonParse).parse(context, responseText);
        } catch (ClientProtocolException e) {
            Logger.error(e.getMessage());
            code = Constants.ErrorCode.ERROR_CODE_NETWORK_ERROR;
        } catch (IOException e) {
            Logger.error(e.getMessage());
            code = Constants.ErrorCode.ERROR_CODE_FILE_ERROR;
        } catch (JSONException e) {
            Logger.error(e.getMessage());
            code = Constants.ErrorCode.ERROR_CODE_PARSE_ERROR;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            code = Constants.ErrorCode.ERROR_CODE_UNKOWN_ERROR;
        }

        return new Result(code, null);
    }

    @Override
    public void doStuffWithResult(Result result) {
        if (result != null) {
            if (result.getCode() == Constants.ErrorCode.SUCCESS) {
                if (successMsg != 0) {
                    Utils.showToast(context, successMsg);
                }
            } else {
                if (failMsg != 0) {
                    if (result.getCode() == Constants.ErrorCode.ERROR_CODE_SERVER_ERROR) {
                        String messaage = (String) result.getData();
                        if (!TextUtils.isEmpty(messaage)) {
                            Utils.showToast(context, messaage);
                        } else {
                            Utils.showToast(context, failMsg);
                        }
                    } else if (result.getCode() == Constants.ErrorCode.ERROR_CODE_DB_ERROR) {
                        Utils.showToast(context, R.string.message_db_error);
                    } else if (result.getCode() == Constants.ErrorCode.ERROR_CODE_FILE_ERROR) {
                        Utils.showToast(context, R.string.message_file_error);
                    } else if (result.getCode() == Constants.ErrorCode.ERROR_CODE_NETWORK_ERROR) {
                        Utils.showToast(context, R.string.message_network_error);
                    } else if (result.getCode() == Constants.ErrorCode.ERROR_CODE_PARSE_ERROR) {
                        Utils.showToast(context, R.string.message_parse_error);
                    } else if (result.getCode() == Constants.ErrorCode.ERROR_CODE_UNKOWN_ERROR) {
                        Utils.showToast(context, R.string.message_unkown_error);
                    }
                }
            }

            if (listener != null) {
                listener.onTaskEnded(id, result.getCode(), result.getData());
            }
        }
    }

    private UserConfig getUserConfig() {
        return UserConfig.getInstance(context);
    }


    public void setId(int id) {
        this.id = id;
    }
}
