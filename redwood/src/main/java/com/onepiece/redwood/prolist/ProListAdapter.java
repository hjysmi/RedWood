package com.onepiece.redwood.prolist;

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
 * Created by Administrator on 2015/8/16.
 */
public class ProListAdapter extends CommonAdapter<ProBean> {
    public ProListAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindDatas(List<ProBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void convert(ViewHolder holder, final ProBean proBean) {
        holder.setText(R.id.id_tv_name, "  "+proBean.getName());
        holder.setText(R.id.id_tv_price, "  ￥ " + proBean.getPrice());
    //    holder.setImageUrl(R.id.id_iv, UrlAPI.IPADDRESS + proBean.getPic() );
        holder.setImageUrl(R.id.id_iv, UrlAPI.IPADDRESS + proBean.getPic()+"_200x200.jpg");
        //添加删除线
       // TextView tv = holder.getView(R.id.id_tv_marketprice);
      //  tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //添加右边分割线
        int position = holder.getPosition();

        View view = holder.getView(R.id.id_right_div);
        if(position%2!=0){//奇数
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        holder.getView(R.id.rl_item_prolistact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProDetailAct.class);
                intent.putExtra(ResultCode.PRO_ID,proBean.getId());
                context.startActivity(intent);
            }
        });
    }
}
