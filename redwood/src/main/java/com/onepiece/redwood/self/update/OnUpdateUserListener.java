package com.onepiece.redwood.self.update;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;
import com.onepiece.redwood.self.UserBean;

/**
 * Created by Administrator on 2015/9/10.
 */
public interface OnUpdateUserListener extends IErrorListener,OnReLoginListener{
    void OnUpdateSuccess(UserBean userBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}
