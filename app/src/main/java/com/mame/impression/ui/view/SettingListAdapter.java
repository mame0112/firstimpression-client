package com.mame.impression.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.SettingFragment;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/04/02.
 */
public class SettingListAdapter extends BaseAdapter{

    private final static String TAG = Constants.TAG + SettingListAdapter.class.getSimpleName();

    private List<SettingFragment.SettingListData> mData = new ArrayList<>();

    private LayoutInflater mInflater;

    public SettingListAdapter(Context context, List<SettingFragment.SettingListData> data){
        super();
        LogUtil.d(TAG, "SettingListAdapter");

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mData.addAll(data);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.d(TAG, "getView");

        ViewHolder viewHolder;

        if(convertView == null){
            LogUtil.d(TAG, "convertview is null");
            convertView = mInflater.inflate(R.layout.setting_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = (TextView) convertView.findViewById(R.id.setting_list_main_text);
            viewHolder.subText = (TextView) convertView.findViewById(R.id.setting_list_sub_text);
            viewHolder.toggleButton = (ToggleButton)convertView.findViewById(R.id.setting_list_toggle);
            convertView.setTag(viewHolder);
        } else {
            LogUtil.d(TAG, "convertview is not null");
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mainText.setText("aa");
        viewHolder.subText.setText("bb");

        return convertView;
    }

    public class ViewHolder {
        TextView mainText;
        TextView subText;
        ToggleButton toggleButton;
    }

}
