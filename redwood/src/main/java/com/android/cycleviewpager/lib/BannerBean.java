package com.android.cycleviewpager.lib;

/**
 * Created by Administrator on 2015/8/21.
 */
public class BannerBean {
    private String content;
    private String url;

    public BannerBean() {
    }

    public BannerBean(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
