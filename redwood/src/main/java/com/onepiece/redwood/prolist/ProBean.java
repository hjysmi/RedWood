package com.onepiece.redwood.prolist;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/16.
 */
public class ProBean {
    @Expose
    private Integer id;
    @Expose
    private Integer customCatId;
    @Expose
    private Integer parentCatId;
    @Expose
    private Integer businessId;
    @Expose
    private String name;
    @Expose
    private String number;
    @Expose
    private Integer count;
    @Expose
    private String pic;
    @Expose
    private Double price;
    @Expose
    private String description;
    @Expose
    private Integer status;
    @Expose
    private String createTime;
    @Expose
    private String material;
    @Expose
    private String style;
    @Expose
    private String standard;
    @Expose
    private Integer sales;

    @Override
    public String toString() {
        return "ProBean{" +
                "businessId=" + businessId +
                ", id=" + id +
                ", customCatId=" + customCatId +
                ", parentCatId=" + parentCatId +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", count=" + count +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", material='" + material + '\'' +
                ", style='" + style + '\'' +
                ", standard='" + standard + '\'' +
                ", sales=" + sales +
                '}';
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCustomCatId() {
        return customCatId;
    }

    public void setCustomCatId(Integer customCatId) {
        this.customCatId = customCatId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getParentCatId() {
        return parentCatId;
    }

    public void setParentCatId(Integer parentCatId) {
        this.parentCatId = parentCatId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
