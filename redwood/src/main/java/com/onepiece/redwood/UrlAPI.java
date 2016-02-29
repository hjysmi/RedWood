package com.onepiece.redwood;

import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.PageBean;
import com.onepiece.redwood.customer.add.AreasBean;
import com.onepiece.redwood.menu.DataBean;
import com.onepiece.redwood.menu.MenuBean;
import com.onepiece.redwood.order.OrderPage;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.prodetail.ProDetailBean;
import com.onepiece.redwood.prolist.ProListBean;
import com.onepiece.redwood.self.UserBean;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

/**
 * IPAddress管理
 */
public interface UrlAPI {
    //Ip地址http://120.25.230.249:3080
    //http://192.168.0.109:8080
    public final String IPADDRESS = "http://120.25.230.249:3080";
    //产品分享页面（二维码扫描链接）http://www.sansha360.com:3080/m/21_details?id=339
    //public final  String  prodetail="/m/21_details?id=339";
    //获取产品分类信息
    @GET("/mobile/getCustomCats")
    public void getMenuInfo(Callback<DataBean<List<MenuBean>>> callback);

    //获取产品列表信息
    @GET("/mobile/product/list")
    public void getProListInfo(@QueryMap Map<String, String> map, Callback<DataBean<ProListBean>> callback);

    /**
     * 获取产品详情
     */
    @GET("/mobile/product/details")
    public void getProDetailInfo(@Query("id") int proId, Callback<DataBean<ProDetailBean>> callback);

    /**
     * 获取所有的客户资料
     */
    @GET("/mobile/productuser/list")
    public void getCustomerInfo(@QueryMap Map<String, String> map, Callback<DataBean<PageBean>> callback);

    /**
     * 获取省市信息
     */
    @GET("/mobile/get/areas")
    public void getAreasInfo(@Query("pcode") int pcode, Callback<DataBean<List<AreasBean>>> callback);

    /**
     * 创建新客户
     */
    @GET("/mobile/save/productuser")
    public void CreateCustomerInfo(@QueryMap Map<String, String> map, Callback<DataBean<CustomerBean>> callback);

    /**
     * 获取客户资料By客户ID
     */
    @GET("/mobile/get/productuser")
    public void getCustomerInfoById(@QueryMap Map<String, String> map, Callback<DataBean<CustomerBean>> callback);

    /**
     * 修改客户资料By客户ID
     */
    @GET("/mobile/save/productuser")
    public void updateCustomerInfoById(@QueryMap Map<String, String> map, Callback<DataBean<CustomerBean>> callback);

    /**
     * 登录
     */
    @GET("/mobile/login")
    public void getUserInfoById(@Query("loginName") String uname, @Query("plainPassword") String psw, Callback<DataBean<UserBean>> callback);

    /**
     * 提交订单
     */
    @GET("/mobile/save/order")
    public void submitOrder(@QueryMap Map<String, String> map, Callback<DataBean<OrderDetailBean>> callback);

    /**
     * 获取订单信息ById
     */
    @GET("/mobile/order/details")
    public void getOrderInfoById(@Query("token") String token, @Query("id") int orderId, Callback<DataBean<OrderDetailBean>> callback);

    /**
     * 获取订单列表
     */
    @GET("/mobile/order/list")
    public void getOrderInfo(@QueryMap() Map<String, String> map, Callback<DataBean<OrderPage>> callback);

    /**
     * 取消订单ById
     */
    @GET("/mobile/order/cancel")
    public void cancelOrderById(@Query("token") String token, @Query("id") int orderId, Callback<DataBean<OrderDetailBean>> callback);
    /**
     * 待处理订单
     */
    @GET("/mobile/order/productuser")
    public void pendingOrder(@Query("token") String token, @Query("orderId") int orderId,@Query("productUserId") int productUserId , Callback<DataBean<Object>> callback);

    /**
     * 上传头像
     */
    @Multipart
    @POST("/mobile/editUser")
    public void upHeadImage(@Query("token") String token,@Part("fileContent") TypedFile file,Callback<DataBean<Object>> callback);
    /**
     * 上传签名图片
     */
    @Multipart
    @POST("/mobile/order/edit")
    public void upSignImage(@Query("token") String token,@Query("orderId")int orderId,@Part("fileContent") TypedFile file,Callback<DataBean<Object>> callback);
    /**
     * 修改用户信息（昵称，电话）
     */
    @GET("/mobile/order/edit")
    public void updateRemarkInfo(@Query("token") String token,@Query("orderId") int orderId,@Query("remark") String remark,Callback<DataBean<Object>> callback);
    /**
     * 修改用户信息（昵称，电话）
     */
    @GET("/mobile/editUser")
    public void updateUserInfo(@QueryMap Map<String,String> map,Callback<DataBean<UserBean>> callback);
}
