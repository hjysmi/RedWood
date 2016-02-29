package com.onepiece.redwood.order.sign;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;

/**
 * Created by Administrator on 2015/9/18.
 */
public interface ISignView extends IErrorView,IReLoginView{
    void hideBar();

    void showBar();

    void showUpSignImageSuccess();

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}
