package com.onepiece.redwood.login;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.self.UserBean;

/**
 * Created by Administrator on 2015/8/28.
 */
public interface OnLoginListenerI extends IErrorListener {
    void OnLoginSuccess(UserBean userBean);
   // void OnLoginFail();

    @Override
    void OnRequestFail();

    @Override
    void OnNetError();

    /*   @Override
    void onReLogin();*/
}
