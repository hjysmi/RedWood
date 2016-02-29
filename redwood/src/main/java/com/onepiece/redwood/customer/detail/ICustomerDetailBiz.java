package com.onepiece.redwood.customer.detail;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/26.
 */
public interface ICustomerDetailBiz {

    void getCustomerDetailInfo(Map<String,String> map,OnCustomerDetailInfoListener onCustomerDetailInfoListener);


}
