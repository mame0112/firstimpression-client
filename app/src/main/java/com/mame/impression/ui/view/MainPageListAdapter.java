package com.mame.impression.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mame.impression.data.MainPageContent;

import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/23.
 */
public class MainPageListAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater mLayoutInflater;
    List<MainPageContent> mContents;

    public MainPageListAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return mContents.size();
    }

    @Override
    public Object getItem(int position) {
        return mContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mContents.get(position).getQuestionId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

//    public void setMainPageAdapterListener()
}
