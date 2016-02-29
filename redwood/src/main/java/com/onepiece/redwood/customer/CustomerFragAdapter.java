package com.onepiece.redwood.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.onepiece.redwood.R;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.customer.detail.CustomerDetailAct;

import java.util.List;

/**
 * Created by Administrator on 2015/8/23.
 */
public class CustomerFragAdapter extends CommonAdapter<CustomerBean> {
    public CustomerFragAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<CustomerBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void convert(ViewHolder holder, final CustomerBean customerBean) {
        holder.setText(R.id.id_tv_name, customerBean.getName());
        holder.setText(R.id.id_tv_info, customerBean.getPhone() + "  " + customerBean.getPcadetail() + "");
        holder.getView(R.id.rlmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerDetailAct.class);
                intent.putExtra(CustomerDetailAct.CUTOMER_ID, customerBean.getId());
                context.startActivity(intent);
            }
        });

    }
}
