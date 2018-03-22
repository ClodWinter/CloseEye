package cn.lizhiyu.closeeye.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lizhiyu on 2017/5/4.
 */

public class ChoiceItemModel implements Serializable
{
    public String title;

    public int cover;

    public String getTitle() {
        return title;
    }

    public int getCover() {
        return cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}
