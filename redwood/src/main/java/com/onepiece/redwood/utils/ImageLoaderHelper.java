package com.onepiece.redwood.utils;

/**
 * Created by Administrator on 2015/10/2.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.onepiece.redwood.R;

import java.io.File;

public class ImageLoaderHelper {

    private Context mContext = null;
    private static volatile ImageLoaderHelper instance = null;

    private ImageLoaderHelper(Context context) {
        mContext = context;
    }

    public static ImageLoaderHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (ImageLoaderHelper.class) {
                if (null == instance) {
                    instance = new ImageLoaderHelper(context);
                }
            }
        }
        return instance;
    }

    public DisplayImageOptions getDisplayOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.headimagegay)
                .showImageForEmptyUri(R.drawable.headimagegay)
                .showImageOnFail(R.drawable.headimagegay)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    public DisplayImageOptions getDisplayOptions(Drawable drawable) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageForEmptyUri(drawable)
                .showImageOnFail(drawable)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    public DisplayImageOptions getDisplayOptions(int round) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(mContext, round)))
                .build();
    }

    public DisplayImageOptions getDisplayOptions(int round, Drawable drawable) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageForEmptyUri(drawable)
                .showImageOnFail(drawable)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(mContext, round)))
                .build();
    }

    public DisplayImageOptions getDisplayOptions(boolean isCacheOnDisk) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.headimagegay)
                .showImageForEmptyUri(R.drawable.headimagegay)
                .showImageOnFail(R.drawable.headimagegay)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(isCacheOnDisk)
                .considerExifParams(true)
                .build();
    }

    public ImageLoaderConfiguration getImageLoaderConfiguration(String filePath) {
        File cacheDir = null;
        if (!StringUtils.isEmpty(filePath)) {
            cacheDir = StorageUtils.getOwnCacheDirectory(mContext, filePath);
        } else {
            cacheDir = StorageUtils.getCacheDirectory(mContext);
        }

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(mContext);
        builder.denyCacheImageMultipleSizesInMemory();

        builder.diskCacheSize(512 * 1024 * 1024);
        builder.diskCacheExtraOptions(720, 1280, null);
        builder.diskCache(new UnlimitedDiscCache(cacheDir));
        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator());

        builder.memoryCacheSizePercentage(14);
        builder.memoryCacheSize(2 * 1024 * 1024);
        builder.memoryCacheExtraOptions(720, 1280);
        builder.memoryCache(new WeakMemoryCache());

        builder.threadPoolSize(3);
        builder.threadPriority(Thread.NORM_PRIORITY - 2);

        builder.defaultDisplayImageOptions(getDisplayOptions());

        return builder.build();
    }
}
