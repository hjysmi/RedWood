package com.onepiece.redwood.self;

import com.onepiece.redwood.self.update.IUpdateUserView;
import com.onepiece.redwood.self.update.OnUpdateUserListener;

import java.util.Map;

import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/9/9.
 */
public class SelfPrestener {
    private ISelfView iSelfView;
    private ISelfBiz iSelfBiz;
    private IUpdateUserView iUpdateUserView;

    public SelfPrestener(ISelfView iSelfView) {
        this.iSelfView = iSelfView;
        this.iSelfBiz = new SelfBiz();
    }

    public SelfPrestener(IUpdateUserView iUpdateUserView) {
        this.iUpdateUserView = iUpdateUserView;
        this.iSelfBiz = new SelfBiz();
    }

    /**
     * 上传头像
     *
     * @param token
     * @param fileToSend
     */
    public void upImage(String token, TypedFile fileToSend) {
        iSelfView.showBar();
        iSelfBiz.upHeadImage(token, fileToSend, new OnUpImageListener() {
            @Override
            public void OnUpImageSuccess() {
                iSelfView.hideBar();
                iSelfView.showUpImageSuccess();
            }

            @Override
            public void OnNetError() {
                iSelfView.hideBar();
                iSelfView.showNetError();
            }

            @Override
            public void onReLogin() {
                iSelfView.hideBar();
                iSelfView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iSelfView.hideBar();
                iSelfView.showRequestError();
            }
        });
    }

    /**
     * 修改用户信息（昵称，电话）
     */
    public void updateUserInfo(Map<String, String> map) {
        iUpdateUserView.showBar();
        iSelfBiz.updateUserInfo(map, new OnUpdateUserListener() {
            @Override
            public void OnUpdateSuccess(UserBean userBean) {
                iUpdateUserView.hideBar();
                iUpdateUserView.showUpdateSuccess(userBean);
            }

            @Override
            public void OnNetError() {
                iUpdateUserView.hideBar();
                iUpdateUserView.showNetError();
            }

            @Override
            public void onReLogin() {
                iUpdateUserView.hideBar();
                iUpdateUserView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iUpdateUserView.hideBar();
                iUpdateUserView.showRequestError();
            }
        });
    }
}
