package com.onepiece.redwood.order.sign;

import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/9/18.
 */
public class SignPresenter {
    private ISignView iSignView;
    private ISignBiz iSignBiz;

    public SignPresenter(ISignView iSignView) {
        this.iSignView = iSignView;
        this.iSignBiz = new SignBiz();
    }

    /**
     * 上传签名图片
     * @param token
     * @param orderId
     * @param fileToSend
     */
    public void upSignImage(String token, int orderId, TypedFile fileToSend) {
        iSignView.showBar();
        iSignBiz.upSignImage(token, orderId, fileToSend, new OnUpSignImageListener() {
            @Override
            public void onUpSignImageSuccess() {
                iSignView.hideBar();
                iSignView.showUpSignImageSuccess();
            }

            @Override
            public void OnNetError() {
                iSignView.hideBar();
                iSignView.showNetError();
            }

            @Override
            public void onReLogin() {
                iSignView.hideBar();
                iSignView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iSignView.hideBar();
                iSignView.showRequestError();
            }
        });
    }
}
