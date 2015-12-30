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
import com.mame.impression.data.AnswerDataBuilder;
import com.mame.impression.data.AnswerPageData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerRecyclerViewFragment extends Fragment implements AnswerPageOverviewAdapter.AnswerPageAdapterListener {

    private static final String TAG = Constants.TAG + AnswerRecyclerViewFragment.class.getSimpleName();

    private ImpressionService mService;

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;

    private AnswerPageOverviewAdapter mAdapter;

    private List<AnswerPageData> mData = new ArrayList<AnswerPageData>();

    private AnswerRecyclerViewListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        mService = ImpressionService.getService(getActivity(), SignInPageFragment.class);

        createDummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer_overview_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.answer_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AnswerPageOverviewAdapter(mData);
        mAdapter.setAdapterClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mService.finalize(this.getClass());
    }

    private void createDummyData(){

        AnswerDataBuilder builder1A = new AnswerDataBuilder();
        AnswerPageData.AnswerData data1A = builder1A.setMale(2).setFemale(3).setUnder10(1).setFrom10_20(4).setFrom20_30(1).setFrom30_40(0).setFrom40_50(5).setFrom50_60(2).setFrom60_70(1).setOver70(1).getResult();
        AnswerDataBuilder builder1B = new AnswerDataBuilder();
        AnswerPageData.AnswerData data1B = builder1B.setMale(2).setFemale(3).setUnder10(1).setFrom10_20(4).setFrom20_30(1).setFrom30_40(0).setFrom40_50(5).setFrom50_60(2).setFrom60_70(1).setOver70(1).getResult();
        List<String> addComments1 = new ArrayList<String>();
        addComments1.add("Comment 1");
        addComments1.add("Comment 2");
        addComments1.add("Comment 3");

        AnswerPageData content1 = new AnswerPageData("description1",data1A, data1B, "additionalQuestion2", addComments1);

        AnswerDataBuilder builder2A = new AnswerDataBuilder();
        AnswerPageData.AnswerData data2A = builder2A.setMale(1).setFemale(0).setUnder10(1).setFrom10_20(2).setFrom20_30(2).setFrom30_40(0).setFrom40_50(2).setFrom50_60(2).setFrom60_70(1).setOver70(1).getResult();
        AnswerDataBuilder builder2B = new AnswerDataBuilder();
        AnswerPageData.AnswerData data2B = builder2B.setMale(2).setFemale(3).setUnder10(2).setFrom10_20(5).setFrom20_30(10).setFrom30_40(6).setFrom40_50(5).setFrom50_60(1).setFrom60_70(1).setOver70(1).getResult();
        List<String> addComments2 = new ArrayList<String>();
        addComments1.add("Comment 4");
        addComments1.add("Comment 5");

        AnswerPageData content2 = new AnswerPageData("description2",data2A, data2B, "additionalQuestion3", addComments2);

        AnswerDataBuilder builder3A = new AnswerDataBuilder();
        AnswerPageData.AnswerData data3A = builder3A.setMale(1).setFemale(0).setUnder10(1).setFrom10_20(2).setFrom20_30(2).setFrom30_40(0).setFrom40_50(2).setFrom50_60(2).setFrom60_70(1).setOver70(1).getResult();
        AnswerDataBuilder builder3B = new AnswerDataBuilder();
        AnswerPageData.AnswerData data3B = builder3B.setMale(2).setFemale(3).setUnder10(2).setFrom10_20(5).setFrom20_30(10).setFrom30_40(6).setFrom40_50(5).setFrom50_60(1).setFrom60_70(1).setOver70(1).getResult();
        List<String> addComments3 = new ArrayList<String>();
        addComments1.add("Comment 6");
        addComments1.add("Comment 7");
        addComments1.add("Comment 8");
        addComments1.add("Comment 9");

        AnswerPageData content3 = new AnswerPageData("description3",data3A, data3B, "additionalQuestion4", addComments3);

        mData.add(content1);
        mData.add(content2);
        mData.add(content3);

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
