package com.onepiece.redwood.prodetail;

import com.google.gson.annotations.Expose;

/**
 * 产品描述
 */
public class ProDescriptionBean {
    @Expose
    private String type;
    @Expose
    private String value;
    public ProDescriptionBean() {
    }

    public ProDescriptionBean(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProDescriptionBean{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
