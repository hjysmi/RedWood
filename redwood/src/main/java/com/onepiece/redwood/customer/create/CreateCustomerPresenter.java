package com.onepiece.redwood.customer.create;

import com.onepiece.redwood.customer.CustomerBean;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/24.
 */
public class CreateCustomerPresenter {
    private ICreateCustomerView iCreateCustomerView;
    private ICreateCustomerBiz iCreateCustomerBiz;

    public CreateCustomerPresenter(ICreateCustomerView iCreateCustomerView) {
        this.iCreateCustomerView = iCreateCustomerView;
        this.iCreateCustomerBiz = new CreateCustomerBiz();
    }

    /**
     * 创建新客户
     *
     * @param map
     */
    public void createCustomerInfo(Map<String, String> map) {
        iCreateCustomerView.showBar();
        iCreateCustomerBiz.createCustomerInfo(map, new OnCreateCustomerListener() {
            @Override
            public void OnCreateSuccess(CustomerBean customerBean) {
                iCreateCustomerView.hideBar();
                iCreateCustomerView.ShowCreateCustomerSuccess(customerBean);
            }

            @Override
            public void OnNetError() {
                iCreateCustomerView.hideBar();
                iCreateCustomerView.showNetError();
            }

            @Override
            public void onReLogin() {
                iCreateCustomerView.hideBar();
                iCreateCustomerView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iCreateCustomerView.hideBar();
                iCreateCustomerView.showRequestError();
            }
        });


    }


}
