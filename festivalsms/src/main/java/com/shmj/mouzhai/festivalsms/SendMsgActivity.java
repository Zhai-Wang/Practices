package com.shmj.mouzhai.festivalsms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shmj.mouzhai.festivalsms.Bean.Festival;
import com.shmj.mouzhai.festivalsms.Bean.FestivalLab;
import com.shmj.mouzhai.festivalsms.Bean.Msg;
import com.shmj.mouzhai.festivalsms.biz.SmsBiz;
import com.shmj.mouzhai.festivalsms.view.FlowLayout;

import java.util.HashSet;

public class SendMsgActivity extends AppCompatActivity {

    public static final String FESTIVAL_ID = "festivalId";
    public static final String MSG_ID = "msgId";
    public static final String ACTION_SEND_MESSAGE = "ACTION_SEND_MESSAGE";
    public static final String ACTION_DELIVER_MESSAGE = "ACTION_DELIVER_MESSAGE";

    private static final int REQUEST_CODE = 1;

    private int mFestivalId;
    private int mMsgId;
    private int mSendMsgCount;
    private int mTotalCount;

    private EditText etMsg;
    private Button btnAdd;
    private FlowLayout flContracts;
    private FloatingActionButton fabSend;
    private LayoutInflater mInflater;
    private View layoutLoading;

    private PendingIntent mSendPendingIntent;
    private PendingIntent mDeliverPendingIntent;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;

    private Festival festival;
    private Msg msg;
    private SmsBiz smsBiz = new SmsBiz();

    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        initData();
        initView();
        initEvent();
        initReceivers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }

    private void initReceivers() {
        Intent sendIntent = new Intent(ACTION_SEND_MESSAGE);
        mSendPendingIntent = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MESSAGE);
        mDeliverPendingIntent = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mSendMsgCount ++;
                if (getResultCode() == RESULT_OK) {
                    Log.e("TAG", "发送成功");
                } else {
                    Log.e("TAG", "发送失败");
                }
                if(mSendMsgCount == mTotalCount){
                    Toast.makeText(SendMsgActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MESSAGE));

        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("TAG", "成功接收");
            }
        }, new IntentFilter(ACTION_DELIVER_MESSAGE));
    }

    private void initEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContactNums.size() == 0) {
                    Toast.makeText(SendMsgActivity.this, "请先选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = etMsg.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(SendMsgActivity.this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                layoutLoading.setVisibility(View.VISIBLE);
                mSendMsgCount = 0;
                mTotalCount = smsBiz.sendMsg(mContactNums, msg, mSendPendingIntent, mDeliverPendingIntent);
            }
        });
    }

    private void initView() {
        mInflater = LayoutInflater.from(this);
        etMsg = (EditText) findViewById(R.id.et_content);
        btnAdd = (Button) findViewById(R.id.btn_add);
        flContracts = (FlowLayout) findViewById(R.id.fl_contracts);
        fabSend = (FloatingActionButton) findViewById(R.id.fab_send);
        layoutLoading = findViewById(R.id.fl_loading);

        layoutLoading.setVisibility(View.GONE);

        if (mMsgId != -1) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.Contacts.DISPLAY_NAME));
                String contactNum = getContactNum(cursor);
                if (!TextUtils.isEmpty(contactNum)) {
                    mContactNames.add(contactName);
                    mContactNums.add(contactNum);
                    addTag(contactName);
                }
            }
        }
    }

    private void addTag(String contactName) {
        TextView textView = (TextView) mInflater.inflate(R.layout.tag, flContracts, false);
        textView.setText(contactName);
        flContracts.addView(textView);
    }

    private String getContactNum(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex
                (ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        if (numberCount > 0) {
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query
                    (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null, null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    public static void toActivity(Context context, int festivalId, int msgId) {
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(FESTIVAL_ID, festivalId);
        intent.putExtra(MSG_ID, msgId);
        context.startActivity(intent);
    }
}
