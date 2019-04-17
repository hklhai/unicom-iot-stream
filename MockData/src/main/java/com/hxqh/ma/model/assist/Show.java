package com.hxqh.ma.model.assist;

import com.hxqh.ma.model.BaiduInfo;
import com.hxqh.ma.model.CrawlerDoubanScore;

/**
 * Created by Ocean lin on 2018/4/20.
 *
 * @author Ocean lin
 */
public class Show {

    private String title;
    private BaiduInfo baiduInfo;
    private CrawlerDoubanScore doubanScore;

    public Show() {
    }

    public Show(String title, BaiduInfo baiduInfo, CrawlerDoubanScore doubanScore) {
        this.title = title;
        this.baiduInfo = baiduInfo;
        this.doubanScore = doubanScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BaiduInfo getBaiduInfo() {
        return baiduInfo;
    }

    public void setBaiduInfo(BaiduInfo baiduInfo) {
        this.baiduInfo = baiduInfo;
    }

    public CrawlerDoubanScore getDoubanScore() {
        return doubanScore;
    }

    public void setDoubanScore(CrawlerDoubanScore doubanScore) {
        this.doubanScore = doubanScore;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Show{");
        sb.append("title='").append(title).append('\'');
        sb.append(", baiduInfo=").append(baiduInfo);
        sb.append(", doubanScore=").append(doubanScore);
        sb.append('}');
        return sb.toString();
    }
}
