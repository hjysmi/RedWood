package com.onepiece.redwood.cart;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 写入数据库中的产品JavaBean(购物车)
 */
@DatabaseTable(tableName="cart")
public class CartBean {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField()
    private Integer proId;
    @DatabaseField()
    private String name;
    @DatabaseField()
    private Double price;
    @DatabaseField()
    private Integer count;
    @DatabaseField()
    private String img;

    public CartBean() {
    }

    public CartBean(Integer proId, Integer count) {
        this.proId = proId;
        this.count = count;
    }

    public CartBean(Integer count, String img, String name, Double price, Integer proId) {
        this.count = count;
        this.img = img;
        this.name = name;
        this.price = price;
        this.proId = proId;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "count=" + count +
                ", id=" + id +
                ", proId=" + proId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }
}
