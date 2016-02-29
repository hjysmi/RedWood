package com.onepiece.redwood;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.utils.ImageLoaderHelper;

/**
 * Created by Administrator on 2015/8/24.
 */
public class BaseApplication extends Application {
   /* public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }*/

  //  private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(ResultCode.Paths.IMAGE_LOADER_CACHE_PATH));
    //    refWatcher = LeakCanary.install(this);
     /*   Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());*/
    }
}
