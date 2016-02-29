package com.onepiece.redwood.cart;

/**
 * 生成订单的JavaBean
 */
public class CartProBean {
    private Integer proId;
    private Integer count;

    public CartProBean(Integer count, Integer proId) {
        this.count = count;
        this.proId = proId;
    }

    @Override
    public String toString() {
        return "CartProBean{" +
                "count=" + count +
                ", proId=" + proId +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }
}
