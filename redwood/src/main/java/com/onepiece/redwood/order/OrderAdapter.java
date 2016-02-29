package com.onepiece.redwood.order;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.order.orderdetail.ProductOrderDetailsBean;
import com.onepiece.redwood.utils.ScreenUtils;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
public class OrderAdapter extends CommonAdapter<ProductOrderDetailsBean> implements View.OnClickListener {
    private Callback mCallback;
    int width;

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface Callback {
        public void click(View v);
    }

    public OrderAdapter( Context context, int layoutId, Callback callback) {
        super(context, layoutId);
        this.mCallback = callback;
        width = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public void bindDatas(List<ProductOrderDetailsBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void convert(ViewHolder holder, final ProductOrderDetailsBean bean) {

        Button id_but_gotrading = holder.getView(R.id.id_but_gotrading);
        Button id_but_del = holder.getView(R.id.id_but_del);
        View line = holder.getView(R.id.line);
        View top = holder.getView(R.id.top);

        OrderDetailBean detailBean = bean.getOrderDetailBean();
        holder.setText(R.id.id_tv_num, "订单: " + detailBean.getOrderNumber());
        holder.setText(R.id.id_tv_time, "下单时间: " + detailBean.getOrderDateTime());
        holder.setText(R.id.tv_name, detailBean.getOrderName());
        holder.setText(R.id.id_tv_total, " ￥" + detailBean.getTotal());
        //
        ImageView iv = holder.getView(R.id.id_iv);
        ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + bean.getProductImg(),iv);
      //  Glide.with(context).load(UrlAPI.IPADDRESS + bean.getProductImg()).into(iv);
        holder.setText(R.id.id_tv_name, bean.getProductName());
        holder.setText(R.id.id_tv_count, "x" + bean.getProductCount());
        //获取第一次出现该订单号的位置
        int mPos = getPositionForSection(bean.getOrderDetailBean().getOrderNumber());
        //获取最后一次出现该订单号的位置
        int mlastPos = getLastPositionForSection(bean.getOrderDetailBean().getOrderNumber());
        if (holder.getPosition() == mPos) {
            top.setVisibility(View.VISIBLE);
            holder.getView(R.id.in_order_item_top).setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
        } else {
            holder.getView(R.id.in_order_item_top).setVisibility(View.GONE);
            top.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
        }
        if (holder.getPosition() == mlastPos) {
            holder.getView(R.id.in_order_item_bottom).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.in_order_item_bottom).setVisibility(View.GONE);
        }

        switch (detailBean.getOrderStatus()) {
            case 0:
                //未完成
                id_but_gotrading.setVisibility(View.GONE);
                id_but_del.setVisibility(View.VISIBLE);
                break;
            case 1:
                //已完成
                id_but_gotrading.setVisibility(View.GONE);
                id_but_del.setVisibility(View.GONE);
                //   del.setVisibility(View.GONE);
                break;
            case 2:
                //已关闭
                id_but_gotrading.setVisibility(View.GONE);
                id_but_del.setVisibility(View.GONE);
                //  del.setVisibility(View.GONE);
                //  ScreenUtils.setMargins(id_tv_real, (int) (0.5 * width), 0, 0, 0);
                break;
            case 3:
                //待处理
                id_but_gotrading.setVisibility(View.VISIBLE);
                //    id_tv_name.setVisibility(View.GONE);
                id_but_del.setVisibility(View.VISIBLE);
                //   ScreenUtils.setMargins(id_tv_total,(int)(0.13*width),0,0,0);
                break;
        }
        PercentLinearLayout view_pro = holder.getView(R.id.order_pro_item);
        view_pro.setTag(bean.getProductId());
        view_pro.setOnClickListener(this);

        id_but_del.setTag(detailBean.getId());
        id_but_del.setOnClickListener(this);

        id_but_gotrading.setTag(detailBean.getId());
        id_but_gotrading.setOnClickListener(this);
        //
        PercentRelativeLayout in_order_item_top = holder.getView(R.id.in_order_item_top);
        in_order_item_top.setOnClickListener(this);
        in_order_item_top.setTag(detailBean.getId());
        //
        PercentRelativeLayout in_order_item_bottom = holder.getView(R.id.in_order_item_bottom);
        in_order_item_bottom.setOnClickListener(this);
        in_order_item_bottom.setTag(detailBean.getId());

    }

    /**
     * 根据订单号获取其第一次出现该订单号的位置
     */
    public int getPositionForSection(String orderNum) {
        for (int i = 0; i < getCount(); i++) {
            String num = datas.get(i).getOrderDetailBean().getOrderNumber();
            if (num == orderNum) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 根据订单号获取其最后1次出现该订单号的位置
     */
    public int getLastPositionForSection(String orderNum) {
        for (int i = getCount() - 1; 0 <= i; i--) {
            String num = datas.get(i).getOrderDetailBean().getOrderNumber();
            if (num == orderNum) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}
