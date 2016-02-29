package com.onepiece.redwood.menu;

import java.util.List;

/**
 * Created by Administrator on 2015/8/15.
 */
public class MenuPresenter {
    private IMenuView iMenuView;
    private IMenuBiz iMenuBiz;

    public MenuPresenter(IMenuView iMenuView) {
        this.iMenuView = iMenuView;
        iMenuBiz = new MenuBiz();
    }

    /**
     * 获取菜单信息
     */
    public void getMenuInfo() {
        iMenuView.showBar();
        iMenuBiz.getMenuInfo(new OnMenuLinstener() {
            @Override
            public void OnMenuSuccess(List<MenuBean> list_menuBean) {
                iMenuView.showMenuSuccess(list_menuBean);
                iMenuView.hideBar();
            }

            @Override
            public void OnNetError() {
                iMenuView.hideBar();
                iMenuView.showNetError();
            }

            @Override
            public void OnRequestFail() {
                iMenuView.hideBar();
                iMenuView.showRequestError();
            }


        });
    }
}
