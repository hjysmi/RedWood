package com.onepiece.redwood.coll;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CollPrestener {
    Context context;
    ICollView iCollView;
    ICollBiz iCollBiz;

    public CollPrestener(Context context, ICollView iCollView) {
        this.context = context;
        this.iCollView = iCollView;
        this.iCollBiz = new CollBiz();
    }

    public void queryCollAll() {
        iCollView.showBar();
        iCollBiz.queryCollAll(context, new OnQueryCollAllListener() {
            @Override
            public void onQueryCollAllSuccess(List<CollBean> collBeanList) {
                iCollView.hideBar();
                iCollView.showCollSuccess(collBeanList);
            }

        });
    }

    /**
     * 删除收藏夹BYID
     *
     * @param proId
     */
    public void delCollById(int proId) {
        iCollView.showBar();
        iCollBiz.deleteCollById(context, proId, new OnDelCollListener() {
            @Override
            public void OnDelColl(List<CollBean> collBeanList) {
                iCollView.hideBar();
                iCollView.delColl(collBeanList);
            }
        });
    }
}
