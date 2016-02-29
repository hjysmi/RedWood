package com.onepiece.redwood.customer;

import com.google.gson.annotations.Expose;
import com.onepiece.redwood.self.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/23.
 */
public class CustomerBean  {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private Integer sex;
    @Expose
    private String phone;
    @Expose
    private String qq;
    @Expose
    private String email;
    @Expose
    private String company;
    @Expose
    private Integer businessId;
    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String address;
    @Expose
    private String industry;
    @Expose
    private String pcadetail;
    @Expose
    private Integer status;
    @Expose
    private Integer count;
    @Expose
    private String createTime;
    @Expose
    private List<UserBean> userList = new ArrayList<UserBean>();


    @Override
    public String toString() {
        return "CustomerBean{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", email=" + email +
                ", company='" + company + '\'' +
                ", businessId=" + businessId +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", industry=" + industry +
                ", pcadetail='" + pcadetail + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", createTime='" + createTime + '\'' +
                ", userList=" + userList +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Object getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPcadetail() {
        return pcadetail;
    }

    public void setPcadetail(String pcadetail) {
        this.pcadetail = pcadetail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }



}
