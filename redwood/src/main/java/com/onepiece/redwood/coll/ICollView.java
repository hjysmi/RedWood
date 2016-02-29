package com.onepiece.redwood.coll;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public interface ICollView {
    void showBar();
    void hideBar();
    void showCollSuccess(List<CollBean> collBeanList);
    void delColl(List<CollBean> collBeanList);
}
