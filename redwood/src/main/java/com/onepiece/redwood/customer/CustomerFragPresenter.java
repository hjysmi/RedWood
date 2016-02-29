package com.onepiece.redwood.customer;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/23.
 */
public class CustomerFragPresenter {
    private ICustomerFragView iCustomerFragView;
    private ICustomerFragBiz iCustomerFragBiz;
    public CustomerFragPresenter(ICustomerFragView iCustomerFragView) {
        this.iCustomerFragView = iCustomerFragView;
        this.iCustomerFragBiz = new CustomerBiz();
    }


    /**
     * 获取客户信息
     * @param map
     */
    public void getCustomerInfo(Map<String, String> map) {
        iCustomerFragView.showBar();
        iCustomerFragBiz.getCustomerFragInfo(map, new OnCustomerFragListener() {
            @Override
            public void OnSuccess(List<CustomerBean> customerBeanList) {
                iCustomerFragView.showCustomerListSuccess(customerBeanList);
                iCustomerFragView.hideBar();
            }

            @Override
            public void OnNetError() {
                iCustomerFragView.hideBar();
                iCustomerFragView.OnNetError();
            }

            @Override
            public void onReLogin() {
                iCustomerFragView.hideBar();
                iCustomerFragView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iCustomerFragView.hideBar();
                iCustomerFragView.OnRequestFail();
            }
        });
    }
}
