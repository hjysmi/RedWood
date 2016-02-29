package com.android.cycleviewpager.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.orhanobut.logger.Logger;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param url
	 * @return
	 */

	public static ImageView getImageView(Context context, String url) {
		ImageView view = (ImageView) LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url,view);
	//	Glide.with(context.getApplicationContext()).load(url).into(view);
	//	ImageLoaderController.getInstance(context).displayIcon(url,view,null);
		Logger.e("img=" + url);
		return view;
	}
}
