package com.onepiece.redwood.order.remark;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface IRemarkView extends IReLoginView,IErrorView{
    void showBar();

    void hideBar();

    void UpDateRemarkSuccess();

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}
