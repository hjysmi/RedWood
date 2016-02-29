package com.onepiece.redwood.menu.libs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.menu.libs.utils.HttpUtils;
import com.onepiece.redwood.menu.libs.utils.MD5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XueTong qq:857417740
 */
public class ImageLoader {
    private static Context context;
    private DefaultImage defaultImage = new DefaultImage();
    //一级缓存的容量
    private static final int MAX_CAPACITY = 5;
    //三级缓存
    //数据结构
    //一级缓存 >>强引用缓存（内存）内存溢出异常
    //20张图片（使用最新的）
    //KEY:图片路径，Value：图片
    //acessOrder true 访问排序 false 插入排序
    //Lru近期最少使用的算法
    private static LinkedHashMap<String, Bitmap> firstCaCheMap = new LinkedHashMap<String, Bitmap>(MAX_CAPACITY, 0.75f, true) {
        //根据返回值，移除map中最老的值
        @Override
        protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
            if (this.size() > MAX_CAPACITY) {
                //加入二级缓存
                secondCacheMap.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                //加入本地缓存
                try {
                    diskCache(eldest.getKey(), eldest.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //移除一级缓存
                return true;
            }

            return false;
        }
    };

    /**
     * 本地缓存
     *
     * @param key   图片路径(图片的路径会被当做图片的名称保存到硬盘上)
     * @param value 图片
     */
    private static void diskCache(String key, Bitmap value) throws IOException {
        //http://ip
        //路径（本地文件标识符）
        //消息摘要算法(抗修改性)Message Diagest Version 5
        String filename = MD5Utils.decode(key);
        String path = context.getCacheDir().getAbsolutePath() + File.separator + filename;
        //JPG
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(path);
            value.compress(Bitmap.CompressFormat.PNG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }


    }

    //二级缓存 >>软引用缓存（内存）内存不足
    //超过20张图片
    //线程安全
    private static ConcurrentHashMap<String, SoftReference<Bitmap>> secondCacheMap = new ConcurrentHashMap<String, SoftReference<Bitmap>>();
    //三级缓存 >>本地缓存（硬盘）
    //离线缓存
    //写入内部存储

    private ImageLoader(Context context) {
        this.context = context;
    }

    private ImageLoader() {
    }

    private static ImageLoader instence;

    public static ImageLoader getInstance(Context context) {
        if (instence == null) {
            instence = new ImageLoader(context);
        }
        return instence;
    }

    /**
     * 加载图片
     *
     * @param key       图片地址
     * @param imageView 图片空间
     */
    public void loadImage(String key, ImageView imageView) {
        //读取缓存
        Bitmap bitmap = getFromCache(key);
        if (bitmap != null) {
            //--?
            //结束该图片对应的所有异步任务
            cancelDownload(key,imageView);
            imageView.setImageBitmap(bitmap);
            System.gc();
        } else {
            //访问网络
            //设置空白图片
        //    imageView.setImageDrawable(defaultImage);

            imageView.setImageResource(R.drawable.headimage);
            //启动异步任务
            AsynImageLoadTask task = new AsynImageLoadTask(imageView);
            task.execute(key);
        }

    }
    public void placeImage(int imageDrawable){

    }
    /**
     *结束该图片对应的所有异步任务
     * @param key
     * @param imageView
     */
    private void cancelDownload(String key, ImageView imageView) {
        //可能有多个异步任务在下载同一张图片
        AsynImageLoadTask task = new AsynImageLoadTask(imageView);
        if(task==null){
            String downloadKey = task.key;
            if(downloadKey==null||!downloadKey.equals(key)){
                //设置标示
                task.cancel(true);
            }
        }
    }

    class AsynImageLoadTask extends AsyncTask<String, Void, Bitmap> {
        //图片地址
        private String key;
        private ImageView imageView;

        public AsynImageLoadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            key = params[0];


            return download(key);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(isCancelled()){
                bitmap=null;
            }
            //
            if(bitmap!=null) {
                //添加到一级缓存
                addFirstCache(key, bitmap);
                //显示
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    /**
     * 添加到一级缓存
     *
     * @param key
     * @param value
     */
    private void addFirstCache(String key, Bitmap value) {
        if (value != null) {
            synchronized (firstCaCheMap) {
                firstCaCheMap.put(key, value);
            }
        }
    }

    /**
     * 下载图片
     *
     * @param key
     * @return
     */
    private Bitmap download(String key) {
        InputStream is = null;
        try {
            is = HttpUtils.download(key);
            return BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    private Bitmap getFromCache(String key) {
        //从一级缓存加载
        synchronized (firstCaCheMap) {
            Bitmap bitmap = firstCaCheMap.get(key);
            //保持图片的新鲜
            if (bitmap != null) {
                firstCaCheMap.remove(key);
                firstCaCheMap.put(key, bitmap);
                return bitmap;
            }
        }
        //从二级缓存加载
        SoftReference<Bitmap> soft_bitmap = secondCacheMap.get(key);
        if (soft_bitmap != null) {
            Bitmap bitmap = soft_bitmap.get();
            if (bitmap != null) {
                //添加到一级缓存
                firstCaCheMap.put(key, bitmap);
                return bitmap;
            }
        } else {
            //软引用被回收了，从缓存中清除
            secondCacheMap.remove(key);

        }

        //从本地缓存加载
        Bitmap local_bitmap = getFromLocal(key);
        if (local_bitmap != null) {
            //添加到一级缓存
            firstCaCheMap.put(key, local_bitmap);
            return local_bitmap;
        }

        return null;
    }

    private Bitmap getFromLocal(String key) {

        String filename = MD5Utils.decode(key);
        if (filename == null) {
            return null;
        }
        String path = context.getCacheDir().getAbsolutePath() + File.separator + filename;
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
           return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    return null;
                }
            }
        }



    }

    /**
     * 默认图片
     */
    static class DefaultImage extends ColorDrawable {
        public DefaultImage() {
            super(Color.WHITE);
        }
    }
}
