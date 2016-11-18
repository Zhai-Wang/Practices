package com.shmj.mouzhai.festivalsms;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shmj.mouzhai.festivalsms.Bean.Festival;
import com.shmj.mouzhai.festivalsms.Bean.FestivalLab;
import com.shmj.mouzhai.festivalsms.Bean.Msg;
import com.shmj.mouzhai.festivalsms.view.FlowLayout;

public class SendMsgActivity extends AppCompatActivity {

    public static final String FESTIVAL_ID = "festivalId";
    public static final String MSG_ID = "msgId";
    private int mFestivalId;
    private int mMsgId;
    private EditText etMsg;
    private Button btnAdd;
    private FlowLayout flContracts;
    private FloatingActionButton fabSend;
    private View layoutLoading;
    private Festival festival;
    private Msg msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        initData();
        initView();
    }

    private void initView() {
        etMsg = (EditText) findViewById(R.id.et_content);
        btnAdd = (Button) findViewById(R.id.btn_add);
        flContracts = (FlowLayout) findViewById(R.id.fl_contracts);
        fabSend = (FloatingActionButton) findViewById(R.id.fab_send);
        layoutLoading = findViewById(R.id.fl_loading);

        layoutLoading.setVisibility(View.GONE);

        if (mMsgId != -1){
            msg = FestivalLab.getInstance().getMsgById(mMsgId);
            etMsg.setText(msg.getContent());
        }
    }

    private void initData() {
        mFestivalId = getIntent().getIntExtra(FESTIVAL_ID, -1);
        mMsgId = getIntent().getIntExtra(MSG_ID, -1);
        festival = FestivalLab.getInstance().getFestivalById(mFestivalId);
        setTitle(festival.getName());
    }

    public static void toActivity(Context context, int festivalId, int msgId){
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(FESTIVAL_ID, festivalId);
        intent.putExtra(MSG_ID, msgId);
        context.startActivity(intent);
    }
}
