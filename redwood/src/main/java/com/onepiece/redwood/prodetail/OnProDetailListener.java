package com.onepiece.redwood.prodetail;

import com.onepiece.redwood.listener.IErrorListener;

/**
 * Created by Administrator on 2015/8/21.
 */
public interface OnProDetailListener extends IErrorListener {
    void getInfoSuccess(ProDetailBean detailBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();
}
