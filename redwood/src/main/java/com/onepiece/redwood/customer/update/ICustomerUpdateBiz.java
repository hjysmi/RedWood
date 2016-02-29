package com.onepiece.redwood.customer.update;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/27.
 */
public interface ICustomerUpdateBiz {
    void updateCustomerInfoById(Map<String,String> map,OnUpdateCustomerListener onUpdateCustomerListener);

}
