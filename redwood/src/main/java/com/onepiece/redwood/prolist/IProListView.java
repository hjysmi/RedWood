package com.onepiece.redwood.prolist;

import com.onepiece.redwood.listener.IErrorView;

import java.util.List;

/**
 * Created by Administrator on 2015/8/16.
 */
public interface IProListView extends IErrorView {
    /**
     * 获取产品列表成功
     *
     * @param list_proBean
     */
    void showProListSuccess(List<ProBean> list_proBean);

    @Override
    void showNetError();

    @Override
    void showRequestError();

    void showBar();

    void hideBar();


}
