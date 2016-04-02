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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/04/01.
 */
public class SettingFragment extends ImpressionBaseFragment {

    private final static String TAG = Constants.TAG + SettingFragment.class.getSimpleName();

    private ListView mListView;

    private SettingListAdapter mAdapter;

    List<SettingListData> mDatas = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        SettingListData data = new SettingListData();
        data.mMainText = "123";
        data.mSubText = "456";

        mDatas.add(data);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.setting_fragment, container, false);

        mAdapter = new SettingListAdapter(getContext(), mDatas);
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

    public static class SettingListData{
        public String mMainText;

        public String mSubText;

    }

}
