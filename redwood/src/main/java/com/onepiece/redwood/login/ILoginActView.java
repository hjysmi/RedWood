package com.onepiece.redwood.login;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.self.UserBean;

/**
 * Created by Administrator on 2015/8/28.
 */
public interface ILoginActView extends IErrorView {
    void showBar();

    void hideBar();

    void ShowLoginSuccess(UserBean userBean);



    String getUname();

    String getUpsw();

    @Override
    void showNetError();

    @Override
    void showRequestError();
}
