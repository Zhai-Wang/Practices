package com.mouzhai.wechattalk.view;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 管理录音进程
 * <p>
 * Created by Mouzhai on 2017/2/16.
 */

public class AudioManager {

    private String mDir;//存储文件夹
    private String mCurrentFilePath;
    public boolean isPrepared;

    private MediaRecorder mMediaRecorder;
    private AudioStateListener mListener;

    private static AudioManager mInstance;

    //单例化构造方法
    private AudioManager(String dir) {
        mDir = dir;
    }

    public static AudioManager getInstance(String dir) {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null)
                    mInstance = new AudioManager(dir);
            }
        }
        return mInstance;
    }

    /**
     * 获取 AudioStateListener 对象
     */
    public void setAudioStateListener(AudioStateListener listener) {
        mListener = listener;
    }

    /**
     * 准备录音
     */
    public void prepareAudio() {
        try {
            //创建保存录音的文件夹
            File dir = new File(mDir);
            if (!dir.exists())
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();

            //随机生成录音文件名,并创建相应文件
            String fileName = UUID.randomUUID().toString() + ".amr";
            File file = new File(dir, fileName);
            mCurrentFilePath = file.getAbsolutePath();
            isPrepared = false;

            mMediaRecorder = new MediaRecorder();
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置录音源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            //准备完毕
            isPrepared = true;
            if (mListener != null)
                mListener.wellPrepared();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前音量等级
     *
     * @return 音量级别
     */
    public int getVoiceLevel(int maxLevel) {
        try {
            if (isPrepared) {
                //mMediaRecorder.getMaxAmplitude() 范围 1 - 32767
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            }
        } catch (Exception e) {
            return 1;
        }
        return 1;
    }

    /**
     * 释放资源
     */
    public void release() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    /**
     * 取消录音
     */
    public void cancelAudio() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            //noinspection ResultOfMethodCallIgnored
            file.delete();
            mCurrentFilePath = null;
        }
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }
}
