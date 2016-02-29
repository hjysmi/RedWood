package com.onepiece.redwood.prolist;

import com.onepiece.redwood.listener.IErrorListener;

/**
 * Created by Administrator on 2015/8/16.
 */
public interface OnProListListener extends IErrorListener {
    void OnProListSuccess(ProListBean proListBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();
}
