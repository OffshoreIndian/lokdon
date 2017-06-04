package com.offshoreindian.lokdon.bean;

/**
 * Created by praveshkumar on 13/11/16.
 */

public class DrawerProviderBean {
    int iconId;
    String providerName;
    int tagId;


    public DrawerProviderBean(int icon, String name, int id) {
        this.iconId = icon;
        this.providerName = name;
        this.tagId = id;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
