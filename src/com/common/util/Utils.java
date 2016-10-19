package com.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Created by zhoutaotao on 6/5/15.
 */
@SuppressLint("SimpleDateFormat")
public class Utils {
    /**
     * 组装中部banner的图片路径
     *
     * @author 杨东
     * @since v1.0
     * @date 2014年12月31日 下午2:40:18
     * @param imageUrl
     * @param width
     * @param height
     * @return
     */
    public static String getPixelImageUrl(String imageUrl, int width, int height) {
        if(checkNullStr(imageUrl)){
            return "";
        }
        String imageBefore = imageUrl.substring(0, imageUrl.lastIndexOf("."));
        Logger.error("imageBefore = " + imageBefore);
        String imageAfter = imageUrl.substring(imageUrl.lastIndexOf(".") + 1, imageUrl.length());
        Logger.error("imageAfter = " + imageAfter);
        String retImageUrl = imageBefore + "_" + width + "x" + height + "." + imageAfter;
        Logger.error("add pixel imageUrl = " + retImageUrl);
        return retImageUrl;
    }

    public static int[] getDeviceWH(Context context) {
        int[] wh = new int[2];
        int w = 0;
        int h = 0;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        w = dm.widthPixels;
        h = dm.heightPixels;
        wh[0] = w;
        wh[1] = h;
        return wh;
    }



    /**
     * 设置配送费文字说明
     *
     * @author 杨东
     * @since v1.0
     * @date 2014年9月22日 下午3:14:13
     * @param sendFees
     * @param onSiteFees
     * @return
     */
    public static String setOnSiteFeesVisibleOrGone(int sendFees, int onSiteFees) {
        if (0 == sendFees && 0 == onSiteFees) {
            // 起送价和配送费都为0
            return "";
        } else if (0 != sendFees && 0 != onSiteFees) {
            // 起送价和配送费都不为0
            return "满" + sendFees + "元免" + onSiteFees + "元配送费";
        } else if (0 != sendFees && 0 == onSiteFees) {
            return sendFees + "元起送";
        } else if (0 == sendFees && 0 != onSiteFees) {
            // 起送价为0，配送费不为0
            return onSiteFees + "元配送费";
        }
        return "";
    }

    /**
     * 获取屏幕宽度
     *
     * @author 杨东
     * @since v1.0
     * @date 2014年9月15日 下午2:43:38
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }
    public static int getHeightWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }


    public static String getImageUrl(String imageUrl) {
        if (checkNullStr(imageUrl)) {
            return "";
        }
        if (imageUrl.startsWith("/")) {
            return imageUrl;
        } else {
            return "/" + imageUrl;
        }
    }

    public static boolean isOnForeground(Context context) {
        boolean isAppRunning = false;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            String MY_PKG_NAME = "com.zhiyi.android.community";
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                    isAppRunning = true;
                    break;
                }
            }
        } catch (Exception e) {
        }
        Logger.error("isOnForeground :" + isAppRunning);
        return isAppRunning;
    }

    public static boolean isOnHomeNewActivity(Context context) {
        boolean isAppRunning = false;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            String MY_PKG_NAME = "com.zhiyi.android.community";
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                    Logger.error("info.topActivity.getClassName() = " + info.topActivity.getClassName());
                    Logger.error("info.baseActivity.getClassName() = " + info.baseActivity.getClassName());
                    if (info.topActivity.getClassName().equals("com.zhiyi.android.community.activity.BaseHomeActivity")) {
                        isAppRunning = true;
                        break;
                    }

                }
            }
        } catch (Exception e) {
        }
        Logger.error("isOnHomeActivity :" + isAppRunning);
        return isAppRunning;
    }


    /**
     * 判断是否是数字
     *
     * @author 杨东
     * @since v1.0
     * @date 2014年7月17日 下午1:26:31
     * @param number
     * @return
     */
    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{" + number.length() + "}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }



    private static final String URL = "http://api.map.baidu.com/geocoder?output=json&address=";

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static double getFloatFormatStr(double number) {
        String str;
        try {
            String parten = "#.##";
            DecimalFormat decimal = new DecimalFormat(parten);
            str = decimal.format(number);
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return 0;
    }

    private static long lastClickTime;
    private static long lastClickTime2;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastDoubleClick2() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime2;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime2 = time;
        return false;
    }


    /**
     * 获取最后一次通话的时长
     *
     * @author 杨东
     * @since v1.0
     * @date 2013年12月26日 下午3:46:25
     * @param phoneNumber
     * @param activity
     * @return
     */
    private static long getLastCallTime(String phoneNumber, Activity activity) {
        try {
            String number = "";
            String duration = "0";
            ContentResolver cr = activity.getContentResolver();
            String selection = CallLog.Calls.NUMBER + "= '" + phoneNumber + "'" + " and " + CallLog.Calls.TYPE + " = " + CallLog.Calls.OUTGOING_TYPE;
            final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[] { CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION,
                    CallLog.Calls._ID }, selection, null, CallLog.Calls.DATE + " DESC");
            if (null == cursor) {
                Logger.error("cursor.getCount() " + 0);
                return 0L;
            }
            Logger.error("cursor.getCount() " + cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                number = cursor.getString(0);
                if (phoneNumber.equals(number)) {
                    duration = cursor.getString(4);
                    break;
                }
            }
            Logger.error("DURATION = " + duration);
            return Long.parseLong(duration);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 设置商家上门条目布局是否显示与隐藏
     *
     * @author 杨东
     * @since v1.0
     * @date 2013年12月26日 下午2:40:17
     * @param number
     * @param layout
     */
    public static void setDisplayOrGone(long number, LinearLayout layout) {
        if (Utils.checkNullStr(number + "") || number <= 0) {
            if (null != layout) {
                layout.setVisibility(View.GONE);
            }
        } else {
            if (null != layout) {
                layout.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * 进入页面时隐藏软键盘
     *
     * @author 杨东
     * @since v1.0
     * @date 2013年12月26日 下午3:53:26
     * @param activity
     */
    public static void hideImm(Activity activity) {
        try {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
        }
    }

    /**
     * 在页面上有焦点后才能隐藏键盘，当前页面需是可见且有焦点
     *
     * @author 杨东
     * @since v1.0
     * @date 2013年12月26日 下午3:53:40
     * @param activity
     */
    public static void hideImmInPage(Activity activity) {
        try {
            ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }



    public static boolean isXiaomiPhone() {
        String model = android.os.Build.MODEL;
        if (checkNullStr(model)) {
            return false;
        }
        model = model.replace(" ", "");
        if (model.equalsIgnoreCase("mi-onec1") || model.equalsIgnoreCase("mi-oneplus") || model.equalsIgnoreCase("mi1s") || model.equalsIgnoreCase("mi1sc") || model.equalsIgnoreCase("mi2")
                || model.equalsIgnoreCase("mi2s") || model.equalsIgnoreCase("mi2sc") || model.equalsIgnoreCase("mi2a") || model.equalsIgnoreCase("mi3") || model.equalsIgnoreCase("2013022")) {
            return true;
        }
        return false;
    }

    /**
     * 判断手机号格式是否正确
     *
     * @author 杨东
     * @since v1.0
     * @date 2013-7-3 下午05:40:21
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if (checkNullStr(mobiles)) {
            return false;
        }
        String REG_EXP = "^((170)|(147)|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(REG_EXP);
        // String REG_EXP_INNER =
        // "^((123)|(147)|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        // Pattern p = Pattern.compile(REG_EXP_INNER);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isFixNumber(String number) {
        String REG_EXP = "^([0]{1}[0-9]{2}-?[0-9]{5,8})|([0]{1}[0-9]{3}-?[0-9]{5,8})|((400)-?[0-9]{4,10})|((800)-?[0-9]{4,10})$";
        return number.matches(REG_EXP);
    }

    /**
     * 检测当前手机是否接入互联网
     *
     * @param context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isValidNumber(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{6}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    // public static void uploadStoreCollect(Context context) {
    // // 同步所有的收藏到服务器端
    // UploadMyCollectBackgroundTask task = new
    // UploadMyCollectBackgroundTask(context);
    // BackgroundThreadManager.getInstance().addTask(task);
    // }

    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return out.toString();
    }

    public static String[] getLocation(String address) {
        String requestUrl = URL + address;
        requestUrl = requestUrl.trim();
        HttpPost request = new HttpPost(requestUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        ResponseHandler<String> reshandler = new BasicResponseHandler();
        try {
            String responseText = client.execute(request, reshandler);
            JSONObject jsonObject = new JSONObject(responseText);
            JSONObject resultObject = jsonObject.getJSONObject("result");
            int precise = resultObject.getInt("precise");
            if (precise != 1) {
                return null;
            }
            JSONObject location = resultObject.getJSONObject("location");
            String[] locationText = new String[2];
            locationText[0] = location.getString("lng");
            locationText[1] = location.getString("lat");
            return locationText;
        } catch (Exception e) {
        }
        return null;
    }

    public static void showDownLoadWeixinDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示").setMessage("本机未安装微信，是否要下载?").setPositiveButton("去下载", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("http://weixin.qq.com");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(it);
                dialog.cancel();
            }
        }).setNegativeButton("取消", null).create().show();
    }

    // 计算两点距离
    private final static double EARTH_RADIUS = 6378137.0;

    public static double gps2m(double lng_a, double lat_a, double lng_b, double lat_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    public static boolean checkSim(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        int status = tm.getSimState();
        if ((status == TelephonyManager.SIM_STATE_UNKNOWN) || (status == TelephonyManager.SIM_STATE_ABSENT)) {
            return false;
        }
        return true;
    }

    public static File getImageFile() {
        File file = null;
        if (Utils.sdCardExist()) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/ImageCache/temp");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String imageName = "temp" + UUID.randomUUID().toString() + ".jpg";
            file = new File(dir, imageName);
        }
        return file;
    }

    /**
     * 得到uri
     *
     * @author 杨东
     * @since v1.0
     * @date 2013-7-17 上午10:56:43
     * @return
     */
    public static Uri getImageUri() {
        Uri imageUri = null;
        if (sdCardExist()) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/ImageCache/temp");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String imageName = "temp" + UUID.randomUUID().toString() + ".jpg";
            File shopPicTempFile = new File(dir, imageName);
            imageUri = Uri.fromFile(shopPicTempFile);
        }
        return imageUri;
    }

    /**
     * 截取字符串的后几位字符
     *
     * @author 杨东
     * @since v1.0
     * @param count
     *            截取几位字符
     * @param str
     *            字符串原串
     * @return
     */
    public static String getSubString(int count, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (count > str.length()) {
            return str;
        }
        return str.substring(str.length() - count, str.length());
    }

    /**
     * 判断list列表是否为空
     *
     * @author 杨东
     * @since v1.0
     * @date 2013-7-1 上午09:34:47
     * @param data
     * @return
     */
    public static boolean isEmpty(List<?> data) {
        if (null == data || data.size() <= 0 || data.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isArrayEmpty(String[] data) {
        if (null == data || data.length <= 0) {
            return true;
        }
        return false;
    }

    public static String getNullStr(String str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str;
    }

    public static boolean checkNullStr(String str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str) || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean checkContainsSpecialWord(String word) {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        // String regEx = "[`~@#$^&*+=|':;'\\[]/@#%”+’|‘；“：？]";
        // String regEx = "[`~@#$%^&*+=|':'\"\"?~@#￥%……&*——+|？]";
        String regEx = "['`\"]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(word);
        return m.find();
    }

    public static int turnDipToPx(float dip, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int turnDipToPx(int dip, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int turnPxToDip(int px, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int turnSpToPx(float spValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }



    public static String sortByCharacter(String input) {
        char[] ch = input.toCharArray();
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < ch.length; i++) {
            list.add(ch[i]);
        }
        Collections.sort(list);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static boolean isGPSOpen(Context c) {
        try {
            LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_status = false;
            if (null != lm) {
                gps_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);// 获得手机是不是设置了GPS开启状态true：gps开启，false：GPS未开启
            }
            return gps_status;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean judgeJsonArray(JSONArray data) {
        if (null == data || data.length() <= 0 || "[]".equals(data) || "null".equalsIgnoreCase(data.toString())) {
            return true;
        }
        return false;
    }

    public static boolean judgeJsonObject(JSONObject data) {
        if (null == data || "".equals(data) || "{}".equals(data) || "null".equalsIgnoreCase(data.toString())) {
            return true;
        }
        return false;
    }



    public static void showToast(Context context, int msgResId) {
        Toast.makeText(context, msgResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean stringEquals(String src, String dest) {
        if (src == null && dest == null) {
            return true;
        } else if (src != null && dest != null) {
            return src.trim().equals(dest.trim());
        } else {
            return false;
        }
    }

    public static boolean stringEqualsIgnoreSpace(String src, String dest) {
        if (src == null && dest == null) {
            return true;
        } else if (src != null && dest != null) {
            return src.equals(dest);
        } else {
            return false;
        }
    }

    public static final String getDeviceImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static final String getSimSerialNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
    }

    public static String getLineNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = telephonyManager.getLine1Number();
        if (number == null) {
            number = "";
        }
        return number;
    }

    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String stringWithUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getUniqueId(Context context) {
        String androidId = "" + getAndroidId(context);
        String deviceId = "" + getDeviceImei(context);
        String simSerialNumber = "" + getSimSerialNumber(context);

        UUID uuid = new UUID(androidId.hashCode(), ((long) deviceId.hashCode() << 32) | simSerialNumber.hashCode());
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    public static boolean validateEmail(String email) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean validatePassword(String password) {
        String strPattern = "^[a-zA-Z0-9]{6,16}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static boolean validateMobilePhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("13") || phoneNumber.startsWith("15") || phoneNumber.startsWith("18")) {
            return true;
        } else {
            return false;
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error(e.getMessage(), e);
        }

        return 0;
    }

    public static String getAppVersionName(Context context) {
        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error(e.getMessage(), e);
        }

        return "";
    }

    public static void JSONObjectToBean(JSONObject json, Object bean) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.length() > 3) {
                String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                String value = null;
                try {
                    value = json.getString(key);
                } catch (JSONException e) {
                }
                if (!TextUtils.isEmpty(value)) {
                    Class<?>[] types = method.getParameterTypes();
                    if (types.length == 1) {
                        Class<?> fieldType = types[0];
                        if (String.class == fieldType) {
                            method.invoke(bean, value);
                        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                            method.invoke(bean, Integer.parseInt(value));
                        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                            method.invoke(bean, Long.parseLong(value));
                        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                            method.invoke(bean, Float.parseFloat(value));
                        } else if (byte[].class == fieldType) {
                            method.invoke(bean, value.getBytes());
                        }
                    }
                }
            }
        }
    }

    public static String beanToJsonString(Object bean) throws InvocationTargetException, IllegalArgumentException, IllegalAccessException {
        /**
         * StringBuilder buffer = new StringBuilder(); buffer.append('{');
         * Class<?> clazz = bean.getClass(); Field[] fields =
         * clazz.getDeclaredFields(); for (Field field : fields) { String name =
         * field.getName(); buffer.append("\"" + name + "\"");
         * field.setAccessible(true); Object value = field.get(bean); if (value
         * instanceof Number) { buffer.append(":" + value); buffer.append(",");
         * } else if (value instanceof String) { buffer.append(":");
         * buffer.append("\"" + value + "\""); buffer.append(","); } }
         * buffer.deleteCharAt(buffer.length() - 1); buffer.append('}'); return
         * buffer.toString();
         */

        StringBuilder buffer = new StringBuilder();
        buffer.append('{');
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && methodName.length() > 3) {
                String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                if (key.equals("class")) {
                    continue;
                }
                Object value = method.invoke(bean, (Object[]) null);
                buffer.append("\"" + key + "\"");
                if (value == null) {
                    buffer.append(":null");
                    buffer.append(",");
                } else {
                    if (value instanceof Number) {
                        buffer.append(":" + value);
                        buffer.append(",");
                    } else if (value instanceof String) {
                        buffer.append(":");
                        buffer.append("\"" + formatJsonString((String) value) + "\"");
                        buffer.append(",");
                    } else if (value instanceof Date) {
                        buffer.append(":");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String text = dateFormat.format(value);
                        buffer.append("\"" + text + "\"");
                        buffer.append(",");
                    } else if (value instanceof byte[]) {
                        buffer.append(":");
                        buffer.append("\"" + new String((byte[]) value) + "\"");
                        buffer.append(",");
                    }
                }
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append('}');
        return buffer.toString();
    }

    private static String formatJsonString(String input) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '\"': {
                    buffer.append("\\\"");
                    break;
                }
                case '\n': {
                    buffer.append("\\n");
                    break;
                }
                case '\b': {
                    buffer.append("\\b");
                    break;
                }
                case '\f': {
                    buffer.append("\\f");
                    break;
                }
                case '\r': {
                    buffer.append("\\r");
                    break;
                }
                case '\t': {
                    buffer.append("\\t");
                    break;
                }
                default: {
                    buffer.append(c);
                }
            }
        }

        return buffer.toString();
    }

    public static boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.reset();
                digest.update(originString.getBytes());
                String resultString = toHexString(digest.digest());
                return resultString.toLowerCase();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        return originString;
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }
}
