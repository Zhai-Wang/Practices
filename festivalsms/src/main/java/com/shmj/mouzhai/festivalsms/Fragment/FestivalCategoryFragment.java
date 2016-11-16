package com.shmj.mouzhai.festivalsms.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.shmj.mouzhai.festivalsms.Bean.Festival;
import com.shmj.mouzhai.festivalsms.Bean.FestivalLab;
import com.shmj.mouzhai.festivalsms.R;

/**
 * 根据 ViewPager 和 Tab 所写的 Fragment 类
 * <p>
 * Created by Mouzhai on 2016/11/16.
 */

public class FestivalCategoryFragment extends Fragment {

    private GridView mGridView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_festival_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());
        mGridView = (GridView) view.findViewById(R.id.gv_festival_category);
        mAdapter = new ArrayAdapter<Festival>(getActivity(), -1, FestivalLab.getInstance().getmList()){
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if(convertView == null){
                    convertView = mInflater.inflate(R.layout.item_festival, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.tv_festival_name);
                textView.setText(getItem(position).getName());
                return convertView;
            }
        };
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Todo
            }
        });
    }
}
