package com.onepiece.redwood.customer.add;

import android.content.Context;

import com.onepiece.redwood.R;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public class AreasChangeAdapter extends CommonAdapter<AreasBean> {

    public AreasChangeAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<AreasBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void convert(ViewHolder holder, AreasBean areasBean) {
        holder.setText(R.id.id_tv_name, areasBean.getName());
    }
}
