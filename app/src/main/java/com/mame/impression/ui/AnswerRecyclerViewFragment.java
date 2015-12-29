package com.mame.impression.ui;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerRecyclerViewFragment extends Fragment {

    private static final String TAG = Constants.TAG + AnswerRecyclerViewFragment.class.getSimpleName();

    private ImpressionService mService;

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        mService = ImpressionService.getService(getActivity(), SignInPageFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer_overview_fragment, container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mService.finalize(this.getClass());
    }

}
