package com.shmj.mouzhai.festivalsms.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmj.mouzhai.festivalsms.Bean.SendedMsg;
import com.shmj.mouzhai.festivalsms.R;
import com.shmj.mouzhai.festivalsms.db.SmsProvider;
import com.shmj.mouzhai.festivalsms.view.FlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SmsHistoryFragment extends ListFragment {

    private static final int LOADER_ID = 1;

    private LayoutInflater inflater;
    private CursorAdapter cursorAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inflater = LayoutInflater.from(getActivity());

        initLoader();
        setUpListAdapter();
    }

    private void setUpListAdapter() {
        cursorAdapter = new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return inflater.inflate(R.layout.fragment_sms_history, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView msg = (TextView) view.findViewById(R.id.tv_msg_history);
                FlowLayout fl = (FlowLayout) view.findViewById(R.id.fl_contacts_history);
                TextView fes = (TextView) view.findViewById(R.id.tv_fes_history);
                TextView date = (TextView) view.findViewById(R.id.tv_date_history);

                msg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_MSG)));
                fes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_FES_NAME)));

                long dateVal = cursor.getLong(cursor.getColumnIndex(SendedMsg.COLUMN_DATE));
                date.setText(parseDate(dateVal));

                String names = cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_NAME));
                if (TextUtils.isEmpty(names)){
                    return;
                }
                fl.removeAllViews();
                for (String name : names.split(":")) {
                    addTag(name, fl);
                }
            }
        };
        setListAdapter(cursorAdapter);
    }

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String parseDate(long dateVal) {
        return dateFormat.format(dateVal);
    }

    private void addTag(String name, FlowLayout fl) {
        TextView tv = (TextView) inflater.inflate(R.layout.tag, fl, false);
        tv.setText(name);
        fl.addView(tv);
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null,
                        null, null, null);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if(loader.getId() == LOADER_ID){
                    cursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                cursorAdapter.swapCursor(null);
            }
        });
    }
}
