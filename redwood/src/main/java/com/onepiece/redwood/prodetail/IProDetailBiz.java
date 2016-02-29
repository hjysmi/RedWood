package com.onepiece.redwood.prodetail;

import android.content.Context;

import com.onepiece.redwood.coll.CollBean;

/**
 * Created by Administrator on 2015/8/21.
 */
public interface IProDetailBiz {
    void getProDetailInfo(int proId,OnProDetailListener onProDetailListener);
    void addCollById(Context context,CollBean collBean, OnCollListener onCollListener);
    void queryCollById(Context context,int proId,OnQueryCollListener onQueryCollListener);
}
