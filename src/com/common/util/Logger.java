package com.common.util;

import android.util.Log;

public final class Logger {
    private static final String TAG = "Community.Android";
    //	 @SuppressWarnings("unused")
    private static final int CLOSE = 99;
    private static final int DEBUG = 1;
    private static final int INFO = 2;
    private static final int WARN = 3;
    private static final int ERROR = 4;

    //控制打印日志级别
    private static int level = DEBUG;

    private Logger() {
    }

    public static void debug(String message) {
        if (level <= DEBUG) {
            Log.d(getTag(), message);
        }
    }

    public static void debug(String message, Throwable error) {
        if (level <= DEBUG) {
            Log.d(getTag(), message, error);
        }
    }

    public static void info(String message) {
        if (level <= INFO) {
            Log.i(getTag(), message);
        }
    }

    public static void info(String message, Throwable error) {
        if (level <= INFO) {
            Log.i(getTag(), message, error);
        }
    }

    public static void warn(String message) {
        if (level <= WARN) {
            Log.w(getTag(), message);
        }
    }

    public static void warn(String message, Throwable error) {
        if (level <= WARN) {
            Log.w(getTag(), message, error);
        }
    }

    public static void error(String message) {
        if (level <= ERROR) {
            Log.e(getTag(), message);
        }
    }

    public static void error(String message, Throwable error) {
        if (level <= ERROR) {
            Log.e(getTag(), message, error);
        }
    }

    public static String getTag() {
        try {
            String s = Log.getStackTraceString(new Throwable());
            if (s != null) {
                String[] sArray = s.split("\n");
                String fullInfo = sArray[3];
                int start = fullInfo.lastIndexOf("at ") + 3;
                int end = fullInfo.lastIndexOf('(');
                String className = fullInfo.substring(start, end);
                start = end + 1;
                end = fullInfo.lastIndexOf(')');
                String location = fullInfo.substring(start, end);
                sArray = location.split(":");
                if (sArray.length >= 2) {
                    String filename = sArray[0];
                    String line = sArray[1];
                    return filename + " - line: " + line;
                } else {
                    return className;
                }
            }
        } catch (Throwable e) {
        }
        return TAG;
    }

    public static void uploadError(String s, Exception e) {

    }
}
