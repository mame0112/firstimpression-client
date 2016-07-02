package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerRecyclerViewFragment extends ImpressionBaseFragment implements AnswerPageOverviewAdapter.AnswerPageAdapterListener {

    private static final String TAG = Constants.TAG + AnswerRecyclerViewFragment.class.getSimpleName();

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;

    private AnswerPageOverviewAdapter mAdapter;

    private List<QuestionResultListData> mData = new ArrayList<QuestionResultListData>();

    private AnswerRecyclerViewListener mListener;

    private TextView mNoContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

    }

    @Override
    public void onResume(){
        super.onResume();

        mNoContentView.setVisibility(View.GONE);
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

        mNoContentView = (TextView)view.findViewById(R.id.answer_page_no_content);
        mNoContentView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void updateData(List<QuestionResultListData> data){
        LogUtil.d(TAG, "updateData");

        //If More than 1 content available
        if(data != null && data.size() != 0){

            if(mNoContentView != null){
                mNoContentView.setVisibility(View.GONE);
            }

            //Copy to another instance
            mData = new ArrayList<QuestionResultListData>(data);

            if(mAdapter != null){
                mAdapter.updateData(data);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            // If no content is available
            LogUtil.d(TAG, "No user generated content available");
            mNoContentView.setVisibility(View.VISIBLE);
        }
    }

    public int getItemCount(){
        if(mData != null){
            return mData.size();
        }
        return 0;
    }


    public void setAnswerRecyclerViewListener(AnswerRecyclerViewListener listener){
        mListener = listener;
    }

    @Override
    public void onViewClicked(View v) {
        int position = mRecyclerView.getChildAdapterPosition(v);
        LogUtil.d(TAG, "onViewClicked: " + position);

        try {
            if(mListener != null){
                mListener.onItemClicked(mData.get(position).getQuestionId());
            }
        } catch (IndexOutOfBoundsException e){
            LogUtil.d(TAG, "IndexOutOfBoundsException: " + e.getMessage());
        }

    }

    @Override
    protected void enterPage() {
        LogUtil.d(TAG, "enterPage");
        AnalyticsTracker.getInstance().trackPage(AnswerRecyclerViewFragment.class.getSimpleName());

        //If Overview data is empty (Meaning user came from notification)
        if(mData == null || mData.size() == 0){
            if(mListener != null){
                mListener.onOverviewFragmentShownWithoutData();
            }
        }
    }

    @Override
    protected void escapePage() {

    }

    public interface AnswerRecyclerViewListener{
        void onItemClicked(long targetQuestionId);

        void onOverviewFragmentShownWithoutData();
    }
}
