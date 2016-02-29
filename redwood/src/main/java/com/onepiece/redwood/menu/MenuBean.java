package com.onepiece.redwood.menu;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/14.
 */
public class MenuBean {
    @Expose
    private Integer id;
    @Expose
    private Integer businessId;
    @Expose
    private String url;
    @Expose
    private Integer type;
    @Expose
    private String name;
    @Expose
    private String subhead;
    @Expose
    private Integer pid;
    @Expose
    private String icon;
    @Expose
    private String bgIcon;

    @Override
    public String toString() {
        return "MenuBean{" +
                "bgIcon='" + bgIcon + '\'' +
                ", id=" + id +
                ", businessId=" + businessId +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", subhead='" + subhead + '\'' +
                ", pid=" + pid +
                ", icon='" + icon + '\'' +
                ", position=" + position +
                ", childProductCustomCat=" + childProductCustomCat +
                ", productBaseinfoCounts='" + productBaseinfoCounts + '\'' +
                ", productBaseinfoList='" + productBaseinfoList + '\'' +
                '}';
    }

    @Expose
    private Integer position;
    @Expose
    private List<MenuBean> childProductCustomCat = new ArrayList<MenuBean>();
    @Expose
    private String productBaseinfoCounts;
    @Expose
    private String productBaseinfoList;

    public MenuBean() {
    }

    public MenuBean(Integer businessId, List<MenuBean> childProductCustomCat, String icon, Integer id, String name, Integer pid, Integer position, String productBaseinfoCounts, String productBaseinfoList, String subhead, Integer type, String url) {
        this.businessId = businessId;
        this.childProductCustomCat = childProductCustomCat;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.position = position;
        this.productBaseinfoCounts = productBaseinfoCounts;
        this.productBaseinfoList = productBaseinfoList;
        this.subhead = subhead;
        this.type = type;
        this.url = url;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public List<MenuBean> getChildProductCustomCat() {
        return childProductCustomCat;
    }

    public void setChildProductCustomCat(List<MenuBean> childProductCustomCat) {
        this.childProductCustomCat = childProductCustomCat;
    }

    public String getBgIcon() {
        return bgIcon;
    }

    public void setBgIcon(String bgIcon) {
        this.bgIcon = bgIcon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getProductBaseinfoCounts() {
        return productBaseinfoCounts;
    }

    public void setProductBaseinfoCounts(String productBaseinfoCounts) {
        this.productBaseinfoCounts = productBaseinfoCounts;
    }

    public String getProductBaseinfoList() {
        return productBaseinfoList;
    }

    public void setProductBaseinfoList(String productBaseinfoList) {
        this.productBaseinfoList = productBaseinfoList;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
