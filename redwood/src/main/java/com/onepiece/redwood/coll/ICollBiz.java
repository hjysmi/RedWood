package com.onepiece.redwood.coll;

import android.content.Context;

/**
 * Created by Administrator on 2015/9/11.
 */
public interface ICollBiz {
    void queryCollAll(Context context,OnQueryCollAllListener onQueryCollAllListener);
    void deleteCollById(Context context,int proId,OnDelCollListener onDelCollListener);
}
