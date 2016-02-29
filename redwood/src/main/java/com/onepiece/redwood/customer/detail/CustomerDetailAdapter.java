package com.onepiece.redwood.customer.detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.order.orderdetail.ProductOrderDetailsBean;
import com.onepiece.redwood.prodetail.ProDetailAct;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

/**
 * Created by Administrator on 2015/9/6.
 */
public class CustomerDetailAdapter extends CommonAdapter<ProductOrderDetailsBean> {


    @Override
    public void convert(ViewHolder holder, final ProductOrderDetailsBean bean) {
        TextView tv_time = holder.getView(R.id.tv_time);
        PercentRelativeLayout customerdetail_pro_top = holder.getView(R.id.customerdetail_pro_top);
        //获取下单时间第一次出现的位置
        OrderDetailBean orderDetailBean = bean.getOrderDetailBean();
        int mPos = getPositionForSection(orderDetailBean.getOrderDateTime());
        if(holder.getPosition()==mPos){
            customerdetail_pro_top.setVisibility(View.VISIBLE);
            tv_time.setText(orderDetailBean.getOrderDateTime());
        }else{
            customerdetail_pro_top.setVisibility(View.GONE);
        }
        holder.setImageUrl(R.id.id_iv, UrlAPI.IPADDRESS+bean.getProductImg()+"_200x200.jpg");
        holder.setText(R.id.tv_name, bean.getProductName());
        holder.setText(R.id.tv_count,"x"+bean.getProductCount());
        holder.setText(R.id.tv_price,"￥ "+bean.getProductPrice());
        holder.getView(R.id.rl_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProDetailAct.class);
                intent.putExtra(ResultCode.PRO_ID,bean.getProductId());
                context.startActivity(intent);
            }
        });
    }

    public CustomerDetailAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<ProductOrderDetailsBean> datas) {
        super.bindDatas(datas);
    }

    /**
     * 根据下单时间获取位置
     *
     * @param orderNum
     * @return
     */
    public int getPositionForSection(String orderNum) {
        for (int i = 0; i < getCount(); i++) {
            String num = datas.get(i).getOrderDetailBean().getOrderDateTime();
            if (num == orderNum) {
                return i;
            }
        }

        return -1;
    }
}
