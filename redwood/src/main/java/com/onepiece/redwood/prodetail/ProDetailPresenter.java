package com.onepiece.redwood.prodetail;

import android.content.Context;
import android.os.Handler;

import com.onepiece.redwood.coll.CollBean;
import com.onepiece.redwood.coll.CollBiz;
import com.onepiece.redwood.coll.ICollBiz;


/**
 * Created by Administrator on 2015/8/21.
 */
public class ProDetailPresenter {
    private Context context;
    private IProdetailView iProdetailView;
    private IProDetailBiz iProDetailBiz;
    private ICollBiz iCollBiz;
    private Handler mHandler = new Handler();

    public ProDetailPresenter(Context context, IProdetailView iProdetailView) {
        this.iProdetailView = iProdetailView;
        this.iProDetailBiz = new ProDetailBiz();
        this.iCollBiz = new CollBiz();
        this.context = context;
    }

    /**
     * 获取产品详情
     *
     * @param proId
     */
    public void getProDetailInfo(int proId) {
        iProdetailView.showBar();
        iProDetailBiz.getProDetailInfo(proId, new OnProDetailListener() {
            @Override
            public void getInfoSuccess(ProDetailBean detailBean) {
                iProdetailView.showDetailSuccess(detailBean);
                iProdetailView.hideBar();
            }

            @Override
            public void OnNetError() {
                iProdetailView.hideBar();
                iProdetailView.showNetError();
            }

            @Override
            public void OnRequestFail() {
                iProdetailView.hideBar();
                iProdetailView.showRequestError();
            }
        });
    }

    /**
     * 加入收藏夹BYId
     *
     * @param collBean
     */
    public void addColl(CollBean collBean) {
        iProdetailView.showBar();
        iProDetailBiz.addCollById(context, collBean, new OnCollListener() {
            @Override
            public void onCollSuccess(int i) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iProdetailView.addCollSuccess();
                        iProdetailView.hideBar();
                    }
                });
            }

            @Override
            public void onCollFail() {
                iProdetailView.addCollFail();
                iProdetailView.hideBar();
            }
        });
    }

    /**
     * 查询收藏夹BYId
     *
     * @param proId
     */
    public void queryCollById(int proId) {
        iProdetailView.showBar();
        iProDetailBiz.queryCollById(context, proId, new OnQueryCollListener() {
            @Override
            public void onQueryCollSuccess() {
                iProdetailView.hideBar();
                iProdetailView.queryCollSuccess();
            }

            @Override
            public void onQueryCollFail() {
                iProdetailView.hideBar();
                iProdetailView.queryCollFail();
            }
        });
    }
}
