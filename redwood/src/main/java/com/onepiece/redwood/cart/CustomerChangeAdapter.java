package com.onepiece.redwood.cart;

import android.content.Context;

import com.onepiece.redwood.R;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.customer.CustomerBean;

import java.util.List;

/**
 * Created by Administrator on 2015/8/23.
 */
public class CustomerChangeAdapter extends CommonAdapter<CustomerBean>  {

    public CustomerChangeAdapter(Context context, int layoutId ) {
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

       /* holder.getView(R.id.rlmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("customerId", customerBean.getId());
                    intent.putExtra("customerName", customerBean.getName());
                    ChangeCustomerAct.instance.setResult(5, intent);
                    ChangeCustomerAct.instance.finish();
            }
        });*/

    }


}
