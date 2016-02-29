package com.onepiece.redwood.customer;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/23.
 */
public interface ICustomerFragBiz {
    void getCustomerFragInfo(Map<String,String> map,OnCustomerFragListener onCustomerFragListener);
}
