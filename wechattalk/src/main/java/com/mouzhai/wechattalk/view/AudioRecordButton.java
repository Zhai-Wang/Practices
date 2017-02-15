package com.mouzhai.wechattalk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mouzhai.wechattalk.R;

/**
 * 自定义录音按钮
 * <p>
 * Created by Mouzhai on 2017/2/8.
 */

public class AudioRecordButton extends Button {

    private final int DISTANCE_Y_CANCEL = 50;
    public final int STATE_RECORD_NORMAL = 0;
    public final int STATE_RECORDING = 1;
    public final int STATE_WANT_CANCEL = 2;

    private int mCurState = STATE_RECORD_NORMAL;//默认状态
    private boolean isRecording = false;//是否正在录音

    private DialogManager mDialogManager;

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDialogManager = new DialogManager(getContext());

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // TODO: 2017/2/15 真正实行应当在 audio end prepared 之后
                mDialogManager.showDialog();
                isRecording = true;
                return false;
            }
        });
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
                    if (wantToCancel(x, y)){
                        changeState(STATE_WANT_CANCEL);
                    }else{
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurState == STATE_RECORDING){
                    // TODO: 2017/2/15 保存录音
                    mDialogManager.dismissDialog();
                }else if (mCurState == STATE_WANT_CANCEL){
                    // TODO: 2017/2/15 取消录音
                    mDialogManager.dismissDialog();
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
        changeState(STATE_RECORD_NORMAL);
    }

    /**
     * 根据用户的手指坐标，判断是否取消录音
     *
     * @param x
     * @param y
     * @return
     */
    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth())
            return true;
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL)
            return true;
        return false;
    }

    /**
     * 根据传入的状态信息，改变按钮样式
     *
     * @param state
     */
    private void changeState(int state) {
        if (mCurState != state){
            mCurState = state;
            switch (state){
                case STATE_RECORD_NORMAL:
                    setBackgroundResource(R.drawable.background_btn_record_normal);
                    setText(R.string.record_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.background_btn_recording);
                    setText(R.string.recording);
                    if (isRecording){
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANT_CANCEL:
                    setBackgroundResource(R.drawable.background_btn_recording);
                    setText(R.string.want_cancel);
                    if (isRecording){
                        mDialogManager.wantToCancel();
                    }
                    break;
            }
        }
    }
}
