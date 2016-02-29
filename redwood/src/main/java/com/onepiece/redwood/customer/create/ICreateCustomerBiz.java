package com.onepiece.redwood.customer.create;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/24.
 */
public interface ICreateCustomerBiz {
    void createCustomerInfo(Map<String,String> map,OnCreateCustomerListener onCreateCustomerListener);

}
