package com.onepiece.redwood.customer.update;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/27.
 */
public class CustomerUpdatePresenter {
    ICustomerUpdateView iCustomerUpdateView;
    ICustomerUpdateBiz iCustomerUpdateBiz;

    public CustomerUpdatePresenter(ICustomerUpdateView iCustomerUpdateView) {
        this.iCustomerUpdateView = iCustomerUpdateView;
        this.iCustomerUpdateBiz = new CustomerUpdateBiz();
    }

    /**
     * 修改客户资料ById
     *
     * @param map
     */
    public void updateCustomerInfo(Map<String, String> map) {
        iCustomerUpdateView.showBar();
        iCustomerUpdateBiz.updateCustomerInfoById(map, new OnUpdateCustomerListener() {
            @Override
            public void OnUpCustomerInfoSuccess() {
                iCustomerUpdateView.updateCustomerInfoSuccess();
                iCustomerUpdateView.hideBar();
            }

            @Override
            public void OnUpCustomerInfoFail() {
                iCustomerUpdateView.updateCustomerInfoFail();
                iCustomerUpdateView.hideBar();
            }
        });
    }
}
