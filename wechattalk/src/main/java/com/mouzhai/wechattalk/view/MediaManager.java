package com.mouzhai.wechattalk.view;

import android.media.*;
import android.media.AudioManager;

import java.io.IOException;

/**
 * 音频播放类
 * <p>
 * Created by Mouzhai on 2017/3/6.
 */

public class MediaManager {

    private static MediaPlayer mediaPlayer;

    private static boolean isPause;

    /**
     * 播放
     */
    public static void playSound(String filePath, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    mediaPlayer.reset();
                    return false;
                }
            });
        } else {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    public static void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 恢复播放
     */
    public static void resume() {
        if (mediaPlayer != null && isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    /**
     * 释放资源
     */
    public static void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
