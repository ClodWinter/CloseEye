package cn.lizhiyu.closeeye.model;

import java.util.ArrayList;

/**
 * Created by king on 2018/3/13.
 */

public class PairingItemModel
{
    public String userName;

    public String sex;

    public String age;

    public String place;

    public String signature;

    public ArrayList tags;

    public Boolean isOnline;

    public int icon;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ArrayList getTags() {
        return tags;
    }

    public void setTags(ArrayList tags) {
        this.tags = tags;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
