package com.onepiece.redwood.prolist;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
public class ProListPrestener {
    IProListView iProListView;
    IProListBiz iProListBiz;

    public ProListPrestener(IProListView iProListView) {
        this.iProListView = iProListView;
        iProListBiz = new ProListBiz();
    }

    /**
     * 获取产品列表信息
     */
    public void getProListInfo(Map<String, String> map) {
        iProListView.showBar();
        iProListBiz.getProListInfo(map, new OnProListListener() {
            @Override
            public void OnProListSuccess(ProListBean proListBean) {

                iProListView.showProListSuccess(proListBean.getResult());
                iProListView.hideBar();
            }

            @Override
            public void OnNetError() {
                iProListView.hideBar();
                iProListView.showNetError();
            }

            @Override
            public void OnRequestFail() {
                iProListView.hideBar();
                iProListView.showRequestError();
            }
        });


    }


}
