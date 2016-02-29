package com.onepiece.redwood.menu.libs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2015/8/13.
 */
public class HttpUtils {
    /**
     * 下载
     * @param key
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static InputStream download(String key) throws MalformedURLException, IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(key).openConnection();
        return conn.getInputStream();
    }
}
