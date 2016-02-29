package com.onepiece.redwood.order.remark;

/**
 * Created by Administrator on 2015/9/21.
 */
public class RemarkPrestener {
    IRemarkBiz iRemarkBiz;
    IRemarkView iRemarkView;

    public RemarkPrestener(IRemarkView iRemarkView) {
        this.iRemarkBiz = new RemarkBiz();
        this.iRemarkView = iRemarkView;
    }

    /**
     * 修改备注
     * @param token
     * @param orderId
     * @param remark
     */
    public void updateRemark(String token, int orderId, String remark) {
        iRemarkView.showBar();
        iRemarkBiz.updateRemark(token, orderId, remark, new OnUpdateRemarkListener() {
            @Override
            public void OnUpdateRemarkSuccess() {
                iRemarkView.hideBar();
                iRemarkView.UpDateRemarkSuccess();
            }

            @Override
            public void OnNetError() {
                iRemarkView.hideBar();
                iRemarkView.showNetError();
            }

            @Override
            public void onReLogin() {
                iRemarkView.hideBar();
                iRemarkView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iRemarkView.hideBar();
                iRemarkView.showRequestError();
            }
        });
    }
}
