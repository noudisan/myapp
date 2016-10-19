package com.common.config;

import android.content.Context;

/**
 * Created by zhoutaotao on 6/5/15.
 */
public class UserConfig {
    private static UserConfig instance;

    // 登录是否记住密码
    private boolean isRememberPwd = true;
    private boolean isShowDot = true;
    private boolean isShowUserDot = true;
    private boolean isFirstTimeToEnterMyStore = true;
    private int hasStore;
    private int viewHelp;
    private int isLogin;
    private String userMail;
    private String pwd;
    private String userName;
    private String deviceId;
    private long sectionUpdateTime;
    private String lastInputEmail;
    private int storeTypeVersion;
    private int defaultCommunitySync;
    private String userAddress = "";
    private int userInfoSync;
    private int viewExplain1;
    private int viewExplain2;
    private int viewExplain3;
    private String city;
    private String frequentlyClickIds;

    // 每个网络接口访问前都需判断当前的code是否为空
    private long goodsUpdateTime;

    private String maskFlag = "";

    private String userPhone = "";

    private long storeAcitvityAccessTime = 0;

    // 标记用户来源，微信登录和小区无忧登录
    private String source;
    private int hasNewVersion;
    private int pullCommunityMsgCount;//保存主动拉取的小区消息数目
    private int msgCount;//保存推送过来的消息数目
    private int personMsgCount;//保存主动拉取的和推送的消息数目
    private int discoverMsgCount;//保存发现页频道消息数目

    private boolean hasAddShortCut = false;


    private boolean isNewLogin = false;//是否第一次登录,用于判断启动页图片

    public boolean isNewLogin() {
        return isNewLogin;
    }

    public void setNewLogin(boolean isNewLogin) {
        this.isNewLogin = isNewLogin;
    }

    private UserConfig(Context context) {
        //FIXME
        //userMail = Constants.UserConfig.NOT_LOGIN_USER_CODE;
        // userName = Constants.UserConfig.NOT_LOGIN_USER_NAME;
        //UserConfigDAO.readUserConfig(context, this);
    }

    public synchronized static UserConfig getInstance(Context context) {
        if (instance == null) {
            instance = new UserConfig(context);
        }
        return instance;
    }

    public void save(Context context) {
        //UserConfigDAO.saveUserConfig(context, this);
    }

    public int getViewHelp() {
        return viewHelp;
    }

    public boolean isShowDot() {
        return isShowDot;
    }

    public void setShowDot(boolean isShowDot) {
        this.isShowDot = isShowDot;
    }

    public boolean isShowUserDot() {
        return isShowUserDot;
    }

    public void setShowUserDot(boolean isShowUserDot) {
        this.isShowUserDot = isShowUserDot;
    }

    public boolean isFirstTimeToEnterMyStore() {
        return isFirstTimeToEnterMyStore;
    }

    public void setFirstTimeToEnterMyStore(boolean isFirstTimeToEnterMyStore) {
        this.isFirstTimeToEnterMyStore = isFirstTimeToEnterMyStore;
    }

    public void setViewHelp(int viewHelp) {
        this.viewHelp = viewHelp;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getSectionUpdateTime() {
        return sectionUpdateTime;
    }

    public void setSectionUpdateTime(long sectionUpdateTime) {
        this.sectionUpdateTime = sectionUpdateTime;
    }

    public String getLastInputEmail() {
        return lastInputEmail;
    }

    public void setLastInputEmail(String lastInputEmail) {
        this.lastInputEmail = lastInputEmail;
    }

    public int getStoreTypeVersion() {
        return storeTypeVersion;
    }

    public void setStoreTypeVersion(int storeTypeVersion) {
        this.storeTypeVersion = storeTypeVersion;
    }

    public int getDefaultCommunitySync() {
        return defaultCommunitySync;
    }

    public void setDefaultCommunitySync(int defaultCommunitySync) {
        this.defaultCommunitySync = defaultCommunitySync;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserInfoSync() {
        return userInfoSync;
    }

    public void setUserInfoSync(int userInfoSync) {
        this.userInfoSync = userInfoSync;
    }

    public int getViewExplain1() {
        return viewExplain1;
    }

    public void setViewExplain1(int viewExplain1) {
        this.viewExplain1 = viewExplain1;
    }

    public int getViewExplain2() {
        return viewExplain2;
    }

    public void setViewExplain2(int viewExplain2) {
        this.viewExplain2 = viewExplain2;
    }

    public int getViewExplain3() {
        return viewExplain3;
    }

    public void setViewExplain3(int viewExplain3) {
        this.viewExplain3 = viewExplain3;
    }

    public boolean isRememberPwd() {
        return isRememberPwd;
    }

    public void setRememberPwd(boolean isRememberPwd) {
        this.isRememberPwd = isRememberPwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMaskFlag() {
        return maskFlag;
    }

    public void setMaskFlag(String maskFlag) {
        this.maskFlag = maskFlag;
    }

    public int getHasStore() {
        return hasStore;
    }

    public void setHasStore(int hasStore) {
        this.hasStore = hasStore;
    }

    public long getGoodsUpdateTime() {
        return goodsUpdateTime;
    }

    public void setGoodsUpdateTime(long goodsUpdateTime) {
        this.goodsUpdateTime = goodsUpdateTime;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public long getStoreAcitvityAccessTime() {
        return storeAcitvityAccessTime;
    }

    public void setStoreAcitvityAccessTime(long storeAcitvityAccessTime) {
        this.storeAcitvityAccessTime = storeAcitvityAccessTime;
    }

    public String getFrequentlyClickIds() {
        return frequentlyClickIds;
    }

    public void setFrequentlyClickIds(String frequentlyClickIds) {
        this.frequentlyClickIds = frequentlyClickIds;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getHasNewVersion() {
        return hasNewVersion;
    }

    public void setHasNewVersion(int hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public int getPullCommunityMsgCount() {
        return pullCommunityMsgCount;
    }

    public void setPullCommunityMsgCount(int pullCommunityMsgCount) {
        this.pullCommunityMsgCount = pullCommunityMsgCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getPersonMsgCount() {
        return personMsgCount;
    }

    public void setPersonMsgCount(int personMsgCount) {
        this.personMsgCount = personMsgCount;
    }

    public int getDiscoverMsgCount() {
        return discoverMsgCount;
    }

    public void setDiscoverMsgCount(int discoverMsgCount) {
        this.discoverMsgCount = discoverMsgCount;
    }

    public boolean isHasAddShortCut() {
        return hasAddShortCut;
    }

    public void setHasAddShortCut(boolean hasAddShortCut) {
        this.hasAddShortCut = hasAddShortCut;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /*public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setIsLogin(this.isLogin);
        userInfo.setCity(city);
        userInfo.setUserAddress(this.getUserAddress());
        userInfo.setUserCode(this.getUserMail());
        userInfo.setUserName(this.getUserName());
        userInfo.setUserPhone(this.getUserPhone());
        userInfo.setLastInputEmail(this.getLastInputEmail());
        return userInfo;
    }*/

}
