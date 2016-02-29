package com.onepiece.redwood.adapter.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2015/8/12.
 */
public class ViewHolder {
    private int mPosition;
    private SparseArray<View> mViews;
    private View mConvertView;
    private ImageLoader imageLoader;
    //  private RequestManager requestManager;
   // private ImageLoader imageLoader;
  //  private ImageLoaderController imageLoader;
    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mViews = new SparseArray<View>();
        this.mPosition = position;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        imageLoader = ImageLoader.getInstance();
      //;  imageLoader = ImageLoaderController.getInstance(context);
       /* new GlideBuilder(context).setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);*/
       //    requestManager = Glide.with(context.getApplicationContext());
       // imageLoader = ImageLoader.getInstance(context);
    }

    public static ViewHolder getView(Context context, ViewGroup parent, View convertView, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }


        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 获取Item的位置
     *
     * @return
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * 加载网络图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        imageLoader.displayImage(url,iv);
        //mImageLoader.loadImage(url, iv, true);
        //    requestManager.load(url).into(iv);
      //  imageLoader.loadImage(url, iv);
        return this;
    }

    public ViewHolder setImageRes(int viewId, int res) {
        ImageView iv = getView(viewId);
        iv.setImageResource(res);

        return this;
    }
}
