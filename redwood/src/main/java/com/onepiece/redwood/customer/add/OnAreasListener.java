package com.onepiece.redwood.customer.add;

import com.onepiece.redwood.listener.IErrorListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public interface OnAreasListener extends IErrorListener {
    void OnAreasSuccess(List<AreasBean> areasBeanList);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();
}
