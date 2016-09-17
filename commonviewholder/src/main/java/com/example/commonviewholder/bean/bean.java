package com.example.commonviewholder.bean;

/**
 * Created by 某宅 on 2016/9/17.
 */
public class Bean {

    String itemTitle;
    String itemContent;
    String itemTime;

    public Bean(String itemTitle, String itemContent, String itemTime) {
        this.itemTitle = itemTitle;
        this.itemContent = itemContent;
        this.itemTime = itemTime;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemContent() {
        return itemContent;
    }

    public String getItemTime() {
        return itemTime;
    }
}
