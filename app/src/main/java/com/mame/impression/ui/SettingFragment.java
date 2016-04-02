package com.mame.impression.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.view.SettingListAdapter;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/04/01.
 */
public class SettingFragment extends ImpressionBaseFragment implements SettingListAdapter.SettingListAdaspterListener{

    private final static String TAG = Constants.TAG + SettingFragment.class.getSimpleName();

    private ListView mListView;

    private SettingListAdapter mAdapter;

    List<SettingListData> mDatas = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        createSettingListData(mDatas);
        restoreDatas(mDatas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.setting_fragment, container, false);

        mAdapter = new SettingListAdapter(this, getContext(), mDatas);
        mListView = (ListView)v.findViewById(R.id.setting_list_view);
        mListView.setAdapter(mAdapter);

        return v;
    }

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }

    private void createSettingListData(List<SettingListData> datas){
        SettingListData data = new SettingListData("1233", "234");
        datas.add(data);
    }

    private void restoreDatas(List<SettingListData> datas){
        for(SettingListData data : datas){
            String mainText = data.getMainText();
            boolean setting = PreferenceUtil.getBooleanParameter(getContext(), mainText);
            data.setCurrentSetting(setting);
        }
    }

    @Override
    public void onSettingChanged(int position, boolean value) {
        LogUtil.d(TAG, "onSettingChanged: " + position + value);
        String mainText = mDatas.get(position).getMainText();
        PreferenceUtil.setBooleanParameter(getContext(), mainText, value);
    }

    public static class SettingListData{
        private String mMainText;

        private String mSubText;

        private boolean mCurrentSetting;

        public SettingListData(String mainText, String subText){
            mMainText = mainText;
            mSubText = subText;
        }

        public String getMainText(){
            return mMainText;
        }

        public String getSubText(){
            return mSubText;
        }

        public void setCurrentSetting(boolean setting){
            mCurrentSetting = setting;
        }

        public boolean getCurrentSetting(){
            return mCurrentSetting;
        }

    }
}
