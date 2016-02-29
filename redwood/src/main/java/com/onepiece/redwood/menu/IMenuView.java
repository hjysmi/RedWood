package com.onepiece.redwood.menu;

import com.onepiece.redwood.listener.IErrorView;

import java.util.List;

/**
 * Created by Administrator on 2015/8/15.
 */
public interface IMenuView extends IErrorView{
    void hideBar();
    void showBar();

    //获取菜单成功
    void showMenuSuccess(List<MenuBean> list_menuBean);

    @Override
    void showNetError();

    @Override
    void showRequestError();
}
