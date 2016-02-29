package com.onepiece.redwood.customer.detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.order.orderdetail.ProductOrderDetailsBean;
import com.onepiece.redwood.prodetail.ProDetailAct;

import java.util.List;

/**
 * Created by Administrator on 2015/9/13.
 */
public class CustomerDetailProAdapter extends CommonAdapter<ProductOrderDetailsBean> {

    public CustomerDetailProAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<ProductOrderDetailsBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void convert(ViewHolder holder, final ProductOrderDetailsBean bean) {
        holder.setText(R.id.tv_name, bean.getProductName());
        holder.setText(R.id.tv_count, "x" + bean.getProductCount());
        holder.setText(R.id.tv_price, "￥ " + bean.getProductPrice());
        holder.setImageUrl(R.id.id_iv, UrlAPI.IPADDRESS + bean.getProductImg());
        holder.getView(R.id.rl_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProDetailAct.class);
                intent.putExtra(ResultCode.PRO_ID, bean.getProductId());
                context.startActivity(intent);
            }
        });
    }
}
