package com.mame.impression.ui;

import android.content.Context;
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

        SettingPreferenceManager.getInstance().createSettingListData(getContext(), mDatas);
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

    @Override
    public void onSettingChanged(int position, boolean value) {
        SettingPreferenceManager.getInstance().updateSettingValue(getContext(), position, value);
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

    public static class SettingPreferenceManager {
        private static SettingPreferenceManager sInstance = new SettingPreferenceManager();

        private SettingPreferenceManager(){
            // Singletone
        }

        public static SettingPreferenceManager getInstance(){
            return sInstance;
        }

        public static void createSettingListData(Context context, List<SettingListData> datas){

            if(context == null){
                throw new IllegalArgumentException("Context cannot be null");
            }

            //Create data set
            SettingListData data = new SettingListData(context.getString(R.string.setting_notification_main_text), context.getString(R.string.setting_notification_sub_text));
            datas.add(data);

            //Restore data
            restoreDatas(context, datas);
        }

        private static void restoreDatas(Context context, List<SettingListData> datas){

            if(context == null){
                throw new IllegalArgumentException("Context cannot be null");
            }

            for(int i=0; i< datas.size();i++){
                SettingListData data = datas.get(i);
                switch(i) {
                    case 0:
                        boolean setting = PreferenceUtil.getNotificationSetting(context);
                        data.setCurrentSetting(setting);
                        break;
                    default:
                        //TODO Need to implement if necessary
                        break;
                }
            }
        }

        public static void updateSettingValue(Context context, int position, boolean value){
            switch(position){
                case 0:
                    PreferenceUtil.setNotificationSetting(context, value);
                    break;
                default:
                    //TODO Need to implement if necessary
                    break;
            }
        }

    }



}
