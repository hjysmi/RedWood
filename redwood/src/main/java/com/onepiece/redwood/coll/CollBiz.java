package com.onepiece.redwood.coll;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CollBiz implements ICollBiz {

    @Override
    public void queryCollAll(Context context, OnQueryCollAllListener onQueryCollAllListener) {
        CollDao dao = new CollDao(context);
        List<CollBean> collBeans = dao.queryCollAll();
        onQueryCollAllListener.onQueryCollAllSuccess(collBeans);

    }

    @Override
    public void deleteCollById(Context context,int proId, OnDelCollListener onDelCollListener) {
        CollDao dao = new CollDao(context);
        int i = dao.delCollByproId(proId);
        if(i>0){
            List<CollBean> collBeans = dao.queryCollAll();
            onDelCollListener.OnDelColl(collBeans);
        }
    }
}
