package com.shmj.mouzhai.festivalsms;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shmj.mouzhai.festivalsms.Bean.Festival;
import com.shmj.mouzhai.festivalsms.Bean.FestivalLab;
import com.shmj.mouzhai.festivalsms.Bean.Msg;
import com.shmj.mouzhai.festivalsms.view.FlowLayout;

import java.util.HashSet;
import java.util.zip.Inflater;

public class SendMsgActivity extends AppCompatActivity {

    public static final String FESTIVAL_ID = "festivalId";
    public static final String MSG_ID = "msgId";
    private static final int REQUEST_CODE = 1;
    private int mFestivalId;
    private int mMsgId;
    private EditText etMsg;
    private Button btnAdd;
    private FlowLayout flContracts;
    private FloatingActionButton fabSend;
    private View layoutLoading;
    private Festival festival;
    private Msg msg;
    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        initData();
        initView();
        initEvent();
    }

    private void initEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.Contacts.DISPLAY_NAME));
                String contactNum = getContactNum(cursor);
                if (!TextUtils.isEmpty(contactNum)){
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
        if (numberCount > 0){
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

    public static void toActivity(Context context, int festivalId, int msgId){
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(FESTIVAL_ID, festivalId);
        intent.putExtra(MSG_ID, msgId);
        context.startActivity(intent);
    }
}
