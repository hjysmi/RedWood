package com.onepiece.redwood.order.remark;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface IRemarkBiz {
    void updateRemark(String token,int orderId,String remark,OnUpdateRemarkListener onUpdateRemarkListener);
}
