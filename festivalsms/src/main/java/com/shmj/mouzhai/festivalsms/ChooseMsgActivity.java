package com.shmj.mouzhai.festivalsms;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shmj.mouzhai.festivalsms.Bean.FestivalLab;
import com.shmj.mouzhai.festivalsms.Bean.Msg;
import com.shmj.mouzhai.festivalsms.Fragment.FestivalCategoryFragment;

import java.util.List;

public class ChooseMsgActivity extends AppCompatActivity {

    private ListView mLvMsg;
    private FloatingActionButton fabToSend;
    private ArrayAdapter<Msg> mAdapter;
    private int mFestivalId;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);

        mInflater = LayoutInflater.from(this);
        mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL, -1);
        setTitle(FestivalLab.getInstance().getFestivalById(mFestivalId).getName());

        initView();
        initEvent();
    }

    private void initEvent() {
        fabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016/11/18  
            }
        });
    }

    private void initView() {
        mLvMsg = (ListView) findViewById(R.id.lv_msg);
        fabToSend = (FloatingActionButton) findViewById(R.id.fab_send_sms);

        mLvMsg.setAdapter(mAdapter = new ArrayAdapter<Msg>(this, -1,
                FestivalLab.getInstance().getMsgsByFestivalId(mFestivalId)){
            @SuppressWarnings("ConstantConditions")
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if(convertView == null){
                    convertView = mInflater.inflate(R.layout.item_lv_msg, parent, false);
                }
                TextView tvContent = (TextView) convertView.findViewById(R.id.tv_msg_content);
                Button btnToSend = (Button) convertView.findViewById(R.id.btn_to_send);

                tvContent.setText(getItem(position).getContent());
                btnToSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 2016/11/18
                    }
                });
                return convertView;
            }
        });
    }
}
