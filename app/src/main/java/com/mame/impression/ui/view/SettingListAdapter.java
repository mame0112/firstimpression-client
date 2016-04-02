package com.mame.impression.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

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

    private SettingListAdaspterListener mListener;

    public SettingListAdapter(SettingListAdaspterListener listener, Context context, List<SettingFragment.SettingListData> data){
        super();
        LogUtil.d(TAG, "SettingListAdapter");

        if(listener == null){
            throw new IllegalArgumentException("SettingListAdaspterListener cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        mListener = listener;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            LogUtil.d(TAG, "convertview is null");
            convertView = mInflater.inflate(R.layout.setting_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = (TextView) convertView.findViewById(R.id.setting_list_main_text);
            viewHolder.subText = (TextView) convertView.findViewById(R.id.setting_list_sub_text);
            viewHolder.switchButton = (Switch)convertView.findViewById(R.id.setting_list_toggle);
            viewHolder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mListener.onSettingChanged(position, isChecked);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            LogUtil.d(TAG, "convertview is not null");
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mainText.setText(mData.get(position).getMainText());
        viewHolder.subText.setText(mData.get(position).getSubText());
        viewHolder.switchButton.setChecked(mData.get(position).getCurrentSetting());

        return convertView;
    }

    public class ViewHolder {
        TextView mainText;
        TextView subText;
        Switch switchButton;
    }

    public interface SettingListAdaspterListener{
        void onSettingChanged(int position, boolean value);
    }

}
