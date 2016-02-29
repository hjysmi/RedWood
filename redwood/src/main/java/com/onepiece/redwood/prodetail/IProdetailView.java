package com.onepiece.redwood.prodetail;

import com.onepiece.redwood.listener.IErrorView;

/**
 * Created by Administrator on 2015/8/21.
 */
public interface IProdetailView extends IErrorView {
    @Override
    void showNetError();

    @Override
    void showRequestError();

    void showDetailSuccess(ProDetailBean detailBean);



    void showBar();

    void hideBar();

    void addCollSuccess();

    void addCollFail();

    void queryCollSuccess();

    void queryCollFail();
}
