package com.onepiece.redwood.customer.contracts;

import com.onepiece.redwood.customer.contracts.lib.SortModel;

import java.util.List;

/**
 * Created by Administrator on 2015/8/25.
 */
public interface IContractView {
    void showDataSuccess(List<SortModel> contactBeanList);
    void showDataFail();
}
