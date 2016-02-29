package com.onepiece.redwood.self;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface ISelfView extends IErrorView, IReLoginView {
    void showBar();

    void hideBar();

    void showUpImageSuccess();

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}
