package com.onepiece.redwood.coll;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 写入数据库中的产品JavaBean(收藏夹)
 */
@DatabaseTable(tableName="coll")
public class CollBean {
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

    public CollBean() {
    }

    public CollBean(String img, String name, Double price, Integer proId) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.proId = proId;
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

    @Override
    public String toString() {
        return "CollBean{" +
                "count=" + count +
                ", id=" + id +
                ", proId=" + proId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                '}';
    }
}
