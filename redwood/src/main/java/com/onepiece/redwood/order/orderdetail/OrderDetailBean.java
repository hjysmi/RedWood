package com.onepiece.redwood.order.orderdetail;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 */
public class OrderDetailBean {
    @Expose
    private Integer id;
    @Expose
    private String orderNumber;
    @Expose
    private String orderName;
    @Expose
    private String orderAddress;
    @Expose
    private String orderPhone;
    @Expose
    private Integer orderStatus;
    @Expose
    private String orderDateTime;
    @Expose
    private Integer userId;
    @Expose
    private String userName;
    @Expose
    private Integer businessId;
    @Expose
    private Object amount;
    @Expose
    private Object quantity;
    @Expose
    private Double total;
    @Expose
    private Integer productUserId;
    @Expose
    private String productUserName;
    @Expose
    private String pdfUrl;
    @Expose
    private String signature;
    @Expose
    private String remark;



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "OrderDetailBean{" +
                "amount=" + amount +
                ", id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderName='" + orderName + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderPhone='" + orderPhone + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderDateTime='" + orderDateTime + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", businessId=" + businessId +
                ", quantity=" + quantity +
                ", total=" + total +
                ", productUserId=" + productUserId +
                ", productUserName='" + productUserName + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", signature='" + signature + '\'' +
                ", remark='" + remark + '\'' +
                ", productOrderDetailsList=" + productOrderDetailsList +
                '}';
    }

    @Expose
    private List<ProductOrderDetailsBean> productOrderDetailsList = new ArrayList<ProductOrderDetailsBean>();

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<ProductOrderDetailsBean> getProductOrderDetailsList() {
        return productOrderDetailsList;
    }

    public void setProductOrderDetailsList(List<ProductOrderDetailsBean> productOrderDetailsList) {
        this.productOrderDetailsList = productOrderDetailsList;
    }

    public Integer getProductUserId() {
        return productUserId;
    }

    public void setProductUserId(Integer productUserId) {
        this.productUserId = productUserId;
    }

    public String getProductUserName() {
        return productUserName;
    }

    public void setProductUserName(String productUserName) {
        this.productUserName = productUserName;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
