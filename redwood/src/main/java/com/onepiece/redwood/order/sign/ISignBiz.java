package com.onepiece.redwood.order.sign;

import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/9/18.
 */
public interface ISignBiz {
    void upSignImage(String token, int orderId, TypedFile file, OnUpSignImageListener onUpSignImageListener);
}
