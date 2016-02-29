package com.onepiece.redwood.login;

import android.content.Context;

import com.onepiece.redwood.self.UserBean;

/**
 * Created by Administrator on 2015/8/28.
 */
public class LoginActPresenter {
    ILoginActView iLoginActView;
    ILoginActBiz iLoginActBiz;
    Context mContext;

    public LoginActPresenter(ILoginActView iLoginActView, Context mContext) {
        this.iLoginActView = iLoginActView;
        this.iLoginActBiz = new LoginActBiz();
        this.mContext = mContext;
    }

    /**
     * 获取登录信息
     */
    public void getLoginInfo() {
        iLoginActView.showBar();
        iLoginActBiz.getUserInfo(iLoginActView.getUname(), iLoginActView.getUpsw(), new OnLoginListenerI() {
            @Override
            public void OnLoginSuccess(UserBean userBean) {
                iLoginActView.ShowLoginSuccess(userBean);
                iLoginActView.hideBar();
            }
            @Override
            public void OnNetError() {
                iLoginActView.showNetError();
                iLoginActView.hideBar();
            }

            @Override
            public void OnRequestFail() {
                iLoginActView.showRequestError();
                iLoginActView.hideBar();
            }
        });
    }
}
