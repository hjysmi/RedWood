package com.onepiece.redwood.customer.add;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/24.
 */
public class AreasBean implements Serializable{
    private Integer id;
    private Integer code;
    private Integer pcode;
    private String name;
    private Object desc1;

    @Override
    public String toString() {
        return "AreasBean{" +
                "code=" + code +
                ", id=" + id +
                ", pcode=" + pcode +
                ", name='" + name + '\'' +
                ", desc1='" + desc1 + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
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

    public Integer getPcode() {
        return pcode;
    }

    public void setPcode(Integer pcode) {
        this.pcode = pcode;
    }
}
