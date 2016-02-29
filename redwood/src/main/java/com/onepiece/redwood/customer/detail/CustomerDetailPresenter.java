package com.onepiece.redwood.customer.detail;

import com.onepiece.redwood.customer.CustomerBean;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/26.
 */
public class CustomerDetailPresenter {
    private ICustomerDetailView iCustomerDetailView;
    private ICustomerDetailBiz iCustomerDetailBiz;

    public CustomerDetailPresenter(ICustomerDetailView iCustomerDetailView) {
        this.iCustomerDetailView = iCustomerDetailView;
        this.iCustomerDetailBiz = new CustomerDetailBiz();
    }

    /**
     * 获取客户信息By客户Id
     * @param map
     */
    public void getCustomerInfoById(Map<String, String> map) {
        iCustomerDetailView.showBar();
        iCustomerDetailBiz.getCustomerDetailInfo(map, new OnCustomerDetailInfoListener() {
            @Override
            public void OnDetailSuccess(CustomerBean customerBean) {
                iCustomerDetailView.ShowCustomerInfoSuccess(customerBean);
                iCustomerDetailView.hideBar();
            }

            @Override
            public void OnNetError() {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.showNetError();
            }

            @Override
            public void onReLogin() {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.showRequestError();
            }
        });
    }
}
