package com.mame.impression.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.AnswerPageData;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerPageOverviewAdapter  extends RecyclerView.Adapter<AnswerPageOverviewAdapter.ViewHolder> {

    private static final String TAG = Constants.TAG + AnswerPageOverviewAdapter.class.getSimpleName();

    private Context mContext;

    private List<AnswerPageData> mData;

    public AnswerPageOverviewAdapter(Context context, List<AnswerPageData> data){
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d(TAG, "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_card_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AnswerPageOverviewAdapter.ViewHolder holder, int position) {
        holder.mDescriptionView.setText(mData.get(position).getDescription());
        holder.mNumOfAnswerView.setText(String.valueOf(mData.get(position).getmAdditionalComments().size()));
        holder.mLastDateView.setText("Test text");
        int addComment = mData.get(position).getmAdditionalComments().size();
        if(addComment != 0){
            holder.mNumOfAdditionView.setVisibility(View.VISIBLE);
            holder.mNumOfAdditionView.setText(String.valueOf(addComment));
        } else {
            holder.mNumOfAdditionView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDescriptionView;
        private TextView mNumOfAnswerView;
        private TextView mLastDateView;
        private TextView mNumOfAdditionView;

        public ViewHolder(View v) {
            super(v);

            mDescriptionView = (TextView)v.findViewById(R.id.answer_card_description);
            mNumOfAnswerView = (TextView)v.findViewById(R.id.answer_card_num_of_answer);
            mLastDateView = (TextView)v.findViewById(R.id.answer_card_last_date);
            mNumOfAdditionView = (TextView)v.findViewById(R.id.answer_card_num_of_addition);

        }
    }
}
