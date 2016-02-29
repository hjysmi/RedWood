package com.onepiece.redwood;

import android.os.Environment;

/**
 * 结果码
 */
public class ResultCode {
    public static final String PRO_ID = "proId";
    //相机返回码
    public static final int SELF_CAMERA_CODE = 0;
    //相册返回码
    public static final int SELF_PHOTO_CODE = 1;
    //启动图片裁剪功能
    public static final int SELF_CROP_CODE = 2;
    //修改self(修改昵称)
    public static final int SELF_NAME_CODE = 3;
    //修改self(修改电话)
    public static final int SELF_PHONE_CODE = 4;
    //
    public static final String USER_UPDATE_NAME = "name";
    public static final String USER_UPDATE_VALUE = "value";
    public static final String USER_UPDATE_KEY = "key";
    public static final String USER_UPDATE_RESULT = "result_value";
    public static final String USER_UPDATE_RESULT_KEY = "result_key";
    public static final String USER_UPDATE_RESULT_DATA = "data";
    public static final int USER_UPDATE_RESULT_CODE = 5;

    //
    public static final String PIC_SHOW_POSITION = "position";
    public static final String PIC_SHOW_IMGURL = "picurl";

    //我的订单（继续交易的返回code）
    public static final int ORDER_TRADING_CODE = 6;
    public static final String ORDER_TARDING_ISTRAD = "istrad";
    public static final String ORDER_TARDING_ISTRADSUCCESS = "istradsuccess";
    public static final String ORDER_TARDING_ISTRADDATA = "data";
    public static final String ORDER_TARDING_ORDERID = "orderId";
    //备注
    public static final String REMARK_TAG = "remark";
    //选择客户
    public static final String IS_CUSTOMER_CHANGE = "customer_change";
    public static final String ACTION_CUSTOMER_CHANGE = "customer_change_action";
    public static final String CUSTOMER_NAME = "customername";
    public static final String CUSTOMER_Id = "customerId";
    //重新登录
    public static final String LOGIN_RELOGIN = "relogin";
    public static final String BROAD_RELOGIN_ACTION_CUSTOMER = "ACTION_CUSTOMER";
    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/com.onepiece.redwood/Images/";
    }
}
