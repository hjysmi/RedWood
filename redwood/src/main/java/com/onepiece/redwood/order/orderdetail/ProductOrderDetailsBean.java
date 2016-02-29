package com.onepiece.redwood.order.orderdetail;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/31.
 */
public class ProductOrderDetailsBean {
    @Expose
    private Integer id;
    @Expose
    private Integer orderId;
    @Expose
    private Integer productId;
    @Expose
    private String productName;
    @Expose
    private String productNumber;
    @Expose
    private Integer productCount;
    @Expose
    private Double productPrice;
    @Expose
    private String productImg;
    @Expose
    private Object total;
    /**
     * 订单信息
     */
    @Expose
    private OrderDetailBean orderDetailBean;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public OrderDetailBean getOrderDetailBean() {
        return orderDetailBean;
    }

    public void setOrderDetailBean(OrderDetailBean orderDetailBean) {
        this.orderDetailBean = orderDetailBean;
    }

    @Override
    public String toString() {
        return "ProductOrderDetailsBean{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productNumber='" + productNumber + '\'' +
                ", productCount=" + productCount +
                ", productPrice=" + productPrice +
                ", productImg='" + productImg + '\'' +
                ", total=" + total +
                ", orderDetailBean=" + orderDetailBean +
                '}';
    }
}
