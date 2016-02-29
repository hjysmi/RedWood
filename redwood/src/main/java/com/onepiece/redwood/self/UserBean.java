package com.onepiece.redwood.self;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/24.
 */
public class UserBean {
    @Expose
    private Integer id;
    @Expose
    private String loginName;
    @Expose
    private String password;
    @Expose
    private Object plainPassword;
    @Expose
    private String salt;
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private String phone;
    @Expose
    private String qq;
    @Expose
    private String status;
    @Expose
    private Integer businessId;
    @Expose
    private Object lastLoginTime;
    @Expose
    private Object jobTitle;
    @Expose
    private Integer type;
    @Expose
    private String token;
    @Expose
    private String headImage;

    public UserBean() {
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "businessId=" + businessId +
                ", id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", plainPassword=" + plainPassword +
                ", salt='" + salt + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", status='" + status + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", jobTitle=" + jobTitle +
                ", type=" + type +
                ", token='" + token + '\'' +
                ", headImage='" + headImage + '\'' +
                '}';
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(Object jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Object getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Object lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(Object plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
