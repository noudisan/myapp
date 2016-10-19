package com.common.util;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public class Constants {

    public class ErrorCode {
        public static final int SUCCESS = 1;
        public static final int ERROR_CODE_SERVER_ERROR = 0;
        public static final int ERROR_CODE_DB_ERROR = -1;
        public static final int ERROR_CODE_FILE_ERROR = -2;
        public static final int ERROR_CODE_NETWORK_ERROR = -3;
        public static final int ERROR_CODE_PARSE_ERROR = -4;
        public static final int ERROR_CODE_UNKOWN_ERROR = -5;

        public static final int OPERATION_CANCELLED = -999;
    }


    public class DeliveryGateWay {
        public static final String DELIVERY_GATEWAY_HOST =
                "http://192.168.1.100:8080";

        public static final String ACTION_LOGIN = DELIVERY_GATEWAY_HOST + "/user/login";
    }
}
