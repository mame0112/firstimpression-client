package com.mame.impression.ui;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListDataBuilder;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerRecyclerViewFragment extends Fragment implements AnswerPageOverviewAdapter.AnswerPageAdapterListener {

    private static final String TAG = Constants.TAG + AnswerRecyclerViewFragment.class.getSimpleName();

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;

    private AnswerPageOverviewAdapter mAdapter;

    private List<QuestionResultListData> mData = new ArrayList<QuestionResultListData>();

    private AnswerRecyclerViewListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer_overview_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.answer_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AnswerPageOverviewAdapter(mData, getActivity());
        mAdapter.setAdapterClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void updateData(List<QuestionResultListData> data){
        LogUtil.d(TAG, "updateData");
        mAdapter.updateData(data);
        mAdapter.notifyDataSetChanged();
    }

    public void setAnswerRecyclerViewListener(AnswerRecyclerViewListener listener){
        mListener = listener;
    }

    @Override
    public void onViewClicked(View v) {
        int position = mRecyclerView.getChildAdapterPosition(v);
        LogUtil.d(TAG, "onViewClicked: " + position);
        if(mListener != null){
            mListener.onItemClicked(position);
        }
    }

    public interface AnswerRecyclerViewListener{
        void onItemClicked(int position);
    }
}
