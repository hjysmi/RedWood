package com.onepiece.redwood.menu;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/8/14.
 */
public class FirstMenuAdapter extends CommonAdapter<MenuBean> {
    int selectedPosition;

    public FirstMenuAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<MenuBean> datas) {
        super.bindDatas(datas);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public void convert(final ViewHolder holder, final MenuBean menuBean) {
        // holder.setImageRes(R.id.id_iv, R.drawable.ic_canting);
        holder.setText(R.id.id_tv, menuBean.getName());
        TextView tv = holder.getView(R.id.id_tv);
        if (holder.getPosition() == selectedPosition) {
            //选中时
            holder.getConvertView().setBackgroundColor(Color.parseColor("#ffffff"));
            tv.setTextColor(Color.parseColor("#b40000"));
            holder.setImageUrl(R.id.id_iv, UrlAPI.IPADDRESS + menuBean.getBgIcon());
        } else {
            //未选中
            holder.getConvertView().setBackgroundColor(Color.parseColor("#f5f5f5"));
            tv.setTextColor(Color.parseColor("#000000"));
            holder.setImageUrl(R.id.id_iv, UrlAPI.IPADDRESS + menuBean.getIcon());
        }
    }
}
