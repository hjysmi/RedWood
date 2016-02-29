package com.onepiece.redwood.customer.add;

import com.onepiece.redwood.listener.IErrorView;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public interface IAreasChangeView extends IErrorView {
    void showBar();

    void hideBar();

    void showAreasSuccess(List<AreasBean> areasBeanList);

    @Override
    void showNetError();

    @Override
    void showRequestError();
}
