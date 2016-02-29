package com.onepiece.redwood.cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.prodetail.ProDetailAct;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
public class CartAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    List<CartBean> list;
    HashMap<CartBean, Boolean> hashMap;
    LayoutInflater inflater;
    CartDao dao;
    static CartAdapter adapter;
    private Callback mCallback;

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface Callback {
        public void click(View v);
    }

    public void bindData(List<CartBean> list) {
        this.list = list;
    }

    public CartAdapter(Context context, Callback callback) {
        this.context = context;
        hashMap = new HashMap<CartBean, Boolean>();
        inflater = LayoutInflater.from(context);
        dao = new CartDao(context);
        adapter = this;
        this.mCallback = callback;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

    class ViewHolder {
        ImageView id_iv;
        TextView id_tv_name, id_tv_price, id_tv_count;
        Button id_but_reduce, id_but_plus, id_but_del;
        PercentRelativeLayout rl_count_change, rl_count;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cartfrag, parent, false);
            holder.id_iv = (ImageView) convertView.findViewById(R.id.id_iv);
            holder.id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_price = (TextView) convertView.findViewById(R.id.id_tv_price);
            holder.id_tv_count = (TextView) convertView.findViewById(R.id.id_tv_count);
            holder.id_but_reduce = (Button) convertView.findViewById(R.id.id_but_reduce);
            holder.id_but_plus = (Button) convertView.findViewById(R.id.id_but_plus);
            holder.id_but_del = (Button) convertView.findViewById(R.id.id_but_del);
            holder.rl_count_change = (PercentRelativeLayout) convertView.findViewById(R.id.rl_count_change);
            holder.rl_count = (PercentRelativeLayout) convertView.findViewById(R.id.rl_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CartBean cartBean = list.get(position);
        //  imageLoader.loadImage(UrlAPI.IPADDRESS + cartBean.getImg(),holder.id_iv);
        ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + cartBean.getImg(),holder.id_iv);
     //   Glide.with(context).load(UrlAPI.IPADDRESS + cartBean.getImg()).into(holder.id_iv);
        holder.id_tv_name.setText(cartBean.getName());
        holder.id_tv_price.setText("￥ " + cartBean.getPrice() + "");
        holder.id_tv_count.setText("" + cartBean.getCount());


        Boolean flag = hashMap.get(cartBean);
        if (flag) {
            //del
            holder.rl_count_change.setVisibility(View.INVISIBLE);
            holder.rl_count_change.setClickable(false);
            holder.id_but_del.setVisibility(View.VISIBLE);
            holder.id_but_del.setClickable(true);
        } else {
            //不是删除
            holder.rl_count_change.setVisibility(View.VISIBLE);
            holder.rl_count_change.setClickable(true);
            holder.id_but_del.setVisibility(View.GONE);
            holder.id_but_del.setClickable(false);
        }
        final int count = Integer.valueOf(holder.id_tv_count.getText().toString().trim());
        holder.id_but_del.setOnClickListener(this);
        holder.id_but_del.setTag(cartBean.getProId());
        holder.id_but_plus.setOnClickListener(this);
        holder.id_but_plus.setTag(new CartBean(cartBean.getProId(), (count + 1)));
        holder.id_but_reduce.setOnClickListener(this);
        holder.id_but_reduce.setTag(new CartBean(cartBean.getProId(), (count - 1)));

        holder.rl_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProDetailAct.class);
                intent.putExtra(ResultCode.PRO_ID, list.get(position).getProId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    public void setHashMap(HashMap<CartBean, Boolean> hashMap) {
        this.hashMap = hashMap;
    }
}
