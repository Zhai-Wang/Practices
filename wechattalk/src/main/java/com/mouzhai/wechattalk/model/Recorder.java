package com.mouzhai.wechattalk.model;

/**
 * Recorder 实体类
 * <p>
 * Created by Mouzhai on 2017/3/6.
 */

public class Recorder {
    float time;
    String filePath;

    public Recorder(float time, String filePath) {
        this.time = time;
        this.filePath = filePath;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
