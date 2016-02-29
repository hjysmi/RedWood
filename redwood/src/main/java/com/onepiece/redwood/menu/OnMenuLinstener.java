package com.onepiece.redwood.menu;

import com.onepiece.redwood.listener.IErrorListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/15.
 */
public interface OnMenuLinstener extends IErrorListener {
    /**
     * 获取菜单成功
     * @return
     */
    void OnMenuSuccess(List<MenuBean> list_menuBean);



    /**
     * 获取菜单失败
     */
    @Override
    void OnRequestFail();

    @Override
    void OnNetError();
}
