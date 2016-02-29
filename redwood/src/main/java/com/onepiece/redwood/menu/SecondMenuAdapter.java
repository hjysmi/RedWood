package com.onepiece.redwood.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.prolist.ProListAct;
import com.onepiece.redwood.utils.ScreenUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

/**
 * Created by Administrator on 2015/8/15.
 */
public class SecondMenuAdapter extends BaseAdapter {
    Context context;
    List<MenuBean> list;
    LayoutInflater inflater;
    // ImageLoader imageLoader;
  //  RequestManager requestManager;
    int width;

    public SecondMenuAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        //  imageLoader = imageLoader.getInstance(context);
        width = ScreenUtils.getScreenWidth(context);
      //  requestManager = Glide.with(context);
    }

    @Override
    public int getCount() {
        return list.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_secondmenu, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.id_iv);
            holder.tv = (TextView) convertView.findViewById(R.id.id_tv);
            holder.tv_all = (TextView) convertView.findViewById(R.id.tv_all);
            holder.layout = (PercentRelativeLayout) convertView.findViewById(R.id.rlmain);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.position = position;
        }
        final MenuBean bean = initBean(position);

        if (position <= list.size() - 1) {
            holder.tv.setText(bean.getName());
            holder.tv_all.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(UrlAPI.IPADDRESS + bean.getIcon(),holder.iv);
        //    requestManager.load(UrlAPI.IPADDRESS + bean.getIcon()).into(holder.iv);
            //    imageLoader.loadImage(UrlAPI.IPADDRESS + bean.getIcon(), holder.iv);
        } else {
            holder.iv.setVisibility(View.INVISIBLE);
            holder.tv.setVisibility(View.INVISIBLE);
            holder.tv_all.setVisibility(View.VISIBLE);
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius((float) (0.6 * width));
            gd.setStroke(2, Color.parseColor("#c2c2c2"));
            gd.setColor(Color.parseColor("#00000000"));
            holder.tv_all.setBackgroundDrawable(gd);
            holder.tv_all.setText("全部");
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProListAct.class);
                intent.putExtra(ProListAct.KEY_SEARCH, false);
                if(position>=list.size()){
                    intent.putExtra(ProListAct.KEY_ID, 0);
                    intent.putExtra(ProListAct.KEY_PID, list.get(0).getPid());
                }else{
                    intent.putExtra(ProListAct.KEY_ID, bean.getId());
                    intent.putExtra(ProListAct.KEY_PID, bean.getPid());
                }

                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private MenuBean initBean(int position) {
        try {
            MenuBean bean = list.get(position);
            return bean;
        } catch (Exception e) {
            return null;
        }

    }

    public void bindData(List<MenuBean> list) {
        this.list = list;
    }

    public class ViewHolder {
        int position;
        TextView tv, tv_all;
        ImageView iv;
        PercentRelativeLayout layout;
    }

}
