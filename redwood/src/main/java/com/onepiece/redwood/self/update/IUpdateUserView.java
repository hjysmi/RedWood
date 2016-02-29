package com.onepiece.redwood.self.update;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;
import com.onepiece.redwood.self.UserBean;

/**
 * Created by Administrator on 2015/9/10.
 */
public interface IUpdateUserView extends IErrorView,IReLoginView {
    void hideBar();

    void showBar();

    void showUpdateSuccess(UserBean userBean);

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}
