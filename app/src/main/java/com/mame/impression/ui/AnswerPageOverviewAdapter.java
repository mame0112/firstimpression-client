package com.mame.impression.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.util.TimeUtil;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerPageOverviewAdapter  extends RecyclerView.Adapter<AnswerPageOverviewAdapter.ViewHolder> implements View.OnClickListener{

    private static final String TAG = Constants.TAG + AnswerPageOverviewAdapter.class.getSimpleName();

    private List<QuestionResultListData> mData;

    private AnswerPageAdapterListener mListener;

    private Context mContext;

    public AnswerPageOverviewAdapter(List<QuestionResultListData> data, Context context){
        mData = data;
        mContext = context;
    }

    public void updateData(List<QuestionResultListData> data){

        //Copy has been done by Activity
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_card_item, parent, false);
        v.setOnClickListener(this);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AnswerPageOverviewAdapter.ViewHolder holder, int position) {
        holder.mDescriptionView.setText(mData.get(position).getDescription());

        holder.mChoiceANumView.setText(String.valueOf(mData.get(position).getNumfOfChoiceA()));
        holder.mChoiceBNumView.setText(String.valueOf(mData.get(position).getNumfOfChoiceB()));

        long lastUpdateDate = mData.get(position).getLastCommentDate();
        holder.mLastDateView.setText(TimeUtil.getDateForDisplay(lastUpdateDate, mContext));
//        holder.mLastDateView.setText("3333");

        int addComment = mData.get(position).getNumOfAdditionalComment();
//        if(addComment != 0){
//            holder.mNumOfAdditionView.setVisibility(View.VISIBLE);
//            holder.mNumOfAdditionView.setText(String.valueOf(addComment));
//        } else {
//            holder.mNumOfAdditionView.setVisibility(View.INVISIBLE);
//        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void setAdapterClickListener(AnswerPageAdapterListener listener){
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(mListener != null){
            mListener.onViewClicked(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDescriptionView;
//        private TextView mNumOfAnswerView;
        private TextView mChoiceANumView;
        private TextView mChoiceBNumView;
        private TextView mLastDateView;
//        private TextView mNumOfAdditionView;

        public ViewHolder(View v) {
            super(v);

            mDescriptionView = (TextView)v.findViewById(R.id.answer_card_description);
//            mNumOfAnswerView = (TextView)v.findViewById(R.id.answer_card_num_of_answer);
            mChoiceANumView = (TextView)v.findViewById(R.id.answer_card_choice_a_num);
            mChoiceBNumView = (TextView)v.findViewById(R.id.answer_card_choice_b_num);
            mLastDateView = (TextView)v.findViewById(R.id.answer_card_card_last_date);
//            mNumOfAdditionView = (TextView)v.findViewById(R.id.answer_card_num_of_addition);

        }
    }

    public interface AnswerPageAdapterListener{
        public void onViewClicked(View v);
    }
}
