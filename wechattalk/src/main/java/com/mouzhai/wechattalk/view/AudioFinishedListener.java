package com.mouzhai.wechattalk.view;

/**
 * 录音完成之后的回调接口
 * <p>
 * Created by Mouzhai on 2017/2/24.
 */

public interface AudioFinishedListener {
    void onFinish(float seconds, String filePath);
}
