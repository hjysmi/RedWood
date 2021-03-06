package com.onepiece.redwood.order.orderdetail;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.prodetail.ProDetailAct;

import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 */
public class OrderDetailAdapter extends CommonAdapter<ProductOrderDetailsBean> {
    public OrderDetailAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<ProductOrderDetailsBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void convert(ViewHolder holder, final ProductOrderDetailsBean proBean) {
        holder.setText(R.id.id_tv_name, proBean.getProductName());
        holder.setText(R.id.id_tv_count, "x" + proBean.getProductCount());
        holder.setText(R.id.id_tv_price, "￥ " + proBean.getProductPrice());
        holder.setImageUrl(R.id.id_iv_img, UrlAPI.IPADDRESS + proBean.getProductImg());
        holder.getView(R.id.id_rlmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProDetailAct.class);
                intent.putExtra(ResultCode.PRO_ID, proBean.getProductId());
                context.startActivity(intent);
            }
        });
    }
}
