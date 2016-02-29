package com.onepiece.redwood.coll;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.adapter.utils.CommonAdapter;
import com.onepiece.redwood.adapter.utils.ViewHolder;
import com.onepiece.redwood.prodetail.ProDetailAct;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class Colladapter extends CommonAdapter<CollBean> implements View.OnClickListener {

    private HashMap<CollBean, Boolean> hashMap;
    private Callback mCallback;

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface Callback {
        public void click(View v);
    }

    public Colladapter(Context context, int layoutId, Callback callback) {
        super(context, layoutId);
        hashMap = new HashMap<CollBean, Boolean>();
        this.mCallback = callback;
    }

    @Override
    public void bindDatas(List<CollBean> datas) {
        super.bindDatas(datas);
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

    @Override
    public void convert(ViewHolder holder, final CollBean collBean) {
        holder.setText(R.id.tv_name, collBean.getName());
        holder.setText(R.id.tv_price, "￥ " + collBean.getPrice());
        ImageView iv = holder.getView(R.id.iv_img);
        Button but_jt = holder.getView(R.id.but_jt);
        Button but_del = holder.getView(R.id.but_del);
     //   Glide.with(context).load(UrlAPI.IPADDRESS + collBean.getImg()).into(iv);
        ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + collBean.getImg(),iv);
        but_del.setOnClickListener(this);
        but_del.setTag(collBean.getProId());
        if (hashMap.get(collBean)) {
            //删除显示箭头隐藏
            but_jt.setVisibility(View.GONE);
            but_del.setVisibility(View.VISIBLE);
        } else {
            //箭头显示删除隐藏
            but_del.setVisibility(View.GONE);
            but_jt.setVisibility(View.VISIBLE);
        }
        holder.getView(R.id.rl_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProDetailAct.class);
                intent.putExtra(ResultCode.PRO_ID, collBean.getProId());
                context.startActivity(intent);
            }
        });

    }

    public void setHashMap(HashMap<CollBean, Boolean> hashMap) {
        this.hashMap = hashMap;
    }
}
