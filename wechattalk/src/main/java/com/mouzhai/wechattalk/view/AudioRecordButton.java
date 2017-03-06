package com.mouzhai.wechattalk.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mouzhai.wechattalk.R;

/**
 * 自定义录音按钮
 * <p>
 * Created by Mouzhai on 2017/2/8.
 */

public class AudioRecordButton extends android.support.v7.widget.AppCompatButton implements AudioStateListener {

    private static final int DISTANCE_Y_CANCEL = 50;//滑动超过这个值，判断为取消录音
    private static final int MAX_VOICE_LEVLE = 7;//最大音量
    private static final int STATE_RECORD_NORMAL = 0;//正常状态
    private static final int STATE_RECORDING = 1;//正在录音状态
    private static final int STATE_WANT_CANCEL = 2;//试图取消录音状态
    private static final int MSG_AUDIO_PREPARED = 3;//录音准备完成
    private static final int MSG_VOICE_CHANGED = 4;//音量改变
    private static final int MSG_DIALOG_DISMISS = 5;//对话框消失

    private int mCurState = STATE_RECORD_NORMAL;//默认状态
    private static boolean isRecording = false;//是否正在录音
    private static float mTime;//录音时长
    private boolean isReady;//是否触发 LongClick

    private static DialogManager mDialogManager;
    private static AudioManager mAudioManager;
    private AudioFinishedListener mAudioFinishedListener;

    //获取音量的子线程
    private static Runnable mVoiceLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    //Audio 准备完成之后，显示 Dialog
                    mDialogManager.showDialog();
                    isRecording = true;
                    //开启子线程，更新音量
                    new Thread(mVoiceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    //获取音量
                    mDialogManager.updateVoice(mAudioManager.getVoiceLevel(MAX_VOICE_LEVLE));
                    break;
                case MSG_DIALOG_DISMISS:
                    mDialogManager.dismissDialog();
                    break;
            }
        }
    };

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        //录音文件保存目录
        String dir = Environment.getExternalStorageDirectory() + "/audio_record";

        mDialogManager = new DialogManager(getContext());
        mAudioManager = AudioManager.getInstance(dir);
        mAudioManager.setAudioStateListener(this);

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isReady = true;
                mAudioManager.prepareAudio();
                return false;
            }
        });
    }

    @Override
    public void wellPrepared() {
        //发送准备完成信息
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手指的位置坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        //根据手指动作，判断应该采取什么反应
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                //如果已经开始录音，判断手指移动区域
                if (isRecording) {
                    //根据手指移动坐标，判断是否取消录音
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //如果没有触发 LongClick，直接重置状态
                if (!isReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                //触发了 LongClick，但是 Audio 尚未准备完成，或录音时间过短
                if (!isRecording || mTime < 0.9f) {
                    mDialogManager.tooShort();
                    mAudioManager.cancelAudio();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS, 1300);
                } else if (mCurState == STATE_RECORDING) {//正常录制结束
                    mAudioManager.release();
                    mDialogManager.dismissDialog();
                    if (mAudioFinishedListener != null)
                        mAudioFinishedListener.onFinish(mTime, mAudioManager.getCurrentFilePath());
                } else if (mCurState == STATE_WANT_CANCEL) {
                    mDialogManager.dismissDialog();
                    mAudioManager.cancelAudio();
                }
                reset();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 重置按钮状态
     */
    private void reset() {
        isRecording = false;
        isReady = false;
        mTime = 0;
        changeState(STATE_RECORD_NORMAL);
    }

    /**
     * 根据用户的手指坐标，判断是否取消录音
     */
    private boolean wantToCancel(int x, int y) {
        return x < 0 || x > getWidth() || y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL;
    }

    /**
     * 根据传入的状态信息，改变按钮样式
     */
    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
            switch (state) {
                case STATE_RECORD_NORMAL:
                    setBackgroundResource(R.drawable.background_btn_record_normal);
                    setText(R.string.record_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.background_btn_recording);
                    setText(R.string.recording);
                    if (isRecording) {
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANT_CANCEL:
                    setBackgroundResource(R.drawable.background_btn_recording);
                    setText(R.string.want_cancel);
                    if (isRecording) {
                        mDialogManager.wantToCancel();
                    }
                    break;
            }
        }
    }

    public void setAudioFinishedListener(AudioFinishedListener audioFinishedListener) {
        mAudioFinishedListener = audioFinishedListener;
    }
}
