package com.onepiece.redwood.customer.add;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public class AreasChangePresenter {
    private IAreasChangeView iAreasChangeView;
    private IAreasChangeBiz iAreasChangeBiz;

    public AreasChangePresenter(IAreasChangeView iAreasChangeView) {
        this.iAreasChangeView = iAreasChangeView;
        this.iAreasChangeBiz = new AreasChangeBiz();
    }

    /**
     * 获取地址信息
     */
    public void getAreas(int pcode) {
        iAreasChangeView.showBar();
        iAreasChangeBiz.getAreasInfo(pcode, new OnAreasListener() {
            @Override
            public void OnAreasSuccess(List<AreasBean> areasBeanList) {
                iAreasChangeView.showAreasSuccess(areasBeanList);
                iAreasChangeView.hideBar();
            }

            @Override
            public void OnNetError() {
                iAreasChangeView.hideBar();
                iAreasChangeView.showNetError();
            }

            @Override
            public void OnRequestFail() {
                iAreasChangeView.hideBar();
                iAreasChangeView.showRequestError();
            }
        });
    }
}
