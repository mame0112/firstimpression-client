package com.mame.impression.ui;

import android.content.Context;
import android.preference.Preference;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.MainPageContent;
import com.mame.impression.ui.view.ButtonUtil;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;
import com.mame.impression.util.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/13.
 */
public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {

    private static final String TAG = Constants.TAG + MainPageAdapter.class.getSimpleName();

    private static List<MainPageContent> mData;

    private static MainPageAdapterListener mListener;

    private int lastPosition = -1;

    private Context mContext;

    private long mMyUserId = Constants.NO_USER;

    public MainPageAdapter(Context context, List<MainPageContent> contents) {
//        super();
//        mData.clear();
//        mData.addAll(contents);
        mData = new ArrayList<>(contents);
        mContext = context;
        mMyUserId = PreferenceUtil.getUserId(context);
    }

    public void updateData(List<MainPageContent> data){
        LogUtil.d(TAG, "updateData: " + data.size());
//        mData = Collections.unmodifiableList(data);
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
//        mData = new ArrayList<>(data);
//        notifyDataSetChanged();
    }

    /**
     * Remove data.
     * return the number of remaining item.
     * @param position
     * @return
     */
    public int remove(int position){
        mData.remove(position);
        return mData.size();
    }

    public void setMainPageAdapterListener(MainPageAdapterListener listener) {
        mListener = listener;
    }

    @Override
    public MainPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_card_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainPageAdapter.ViewHolder holder, int position) {
        MainPageContent data = mData.get(position);

        if(data != null){
            //TODO
//            holder.mThumbnail.setImageBitmap(data.getThumbnail());
            //TODO
            holder.mPostDateView.setText(TimeUtil.getDateForDisplay(data.getPostDate(), mContext));
            holder.mUserName.setText(data.getUserName());
            holder.mDescription.setText(data.getDescription());
            holder.mChoiseAButton.setText(data.getChoiceA());
            holder.mChoiseBButton.setText(data.getChoiceB());

            //TODO Need to disable
            //If this question was created by this user, disable button to avoid the user respond this question
//            if(data.getCreatedUserId() == mMyUserId){
//                holder.mChoiseAButton.setEnabled(false);
//                holder.mChoiseBButton.setEnabled(false);
//            }

        }
//        holder.mThumbnail.setImageBitmap(mData.get(position).getThumbnail());
//        holder.mPostDateView.setText(mData.get(position).getPostDate());
//        holder.mUserName.setText(mData.get(position).getUserName());
//        holder.mDescription.setText(mData.get(position).getDescription());
//        holder.mChoiseAButton.setText(mData.get(position).getChoiceA());
//        holder.mChoiseBButton.setText(mData.get(position).getChoiceB());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public interface MainPageAdapterListener {
        public void onItemSelected(long id, int select);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ImageView mThumbnail;
        public TextView mPostDateView;
        public TextView mUserName;
        public TextView mDescription;
        public Button mChoiseAButton;
        public Button mChoiseBButton;

        public ViewHolder(View v) {
            super(v);
            mThumbnail = (ImageView) v.findViewById(R.id.main_card_thumbnail);

            mPostDateView = (TextView) v.findViewById(R.id.main_card_postdate);
            mUserName = (TextView) v.findViewById(R.id.main_card_username);
            mDescription = (TextView) v.findViewById(R.id.main_card_description);

            mChoiseAButton = (Button) v.findViewById(R.id.main_card_choise_a);
            mChoiseAButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.d(TAG, "onClick");
//                    if(ButtonUtil.isClickable()){
                        LogUtil.d(TAG, "clikable");
                        long id = mData.get(getAdapterPosition()).getQuestionId();
                        mData.remove(getAdapterPosition()).getQuestionId();
                        mListener.onItemSelected(id, 0);
//                    }
                }
            });

            mChoiseBButton = (Button) v.findViewById(R.id.main_card_choise_b);
            mChoiseBButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(ButtonUtil.isClickable()){
                        long id = mData.get(getAdapterPosition()).getQuestionId();
                        mData.remove(getAdapterPosition()).getQuestionId();
                        mListener.onItemSelected(id, 1);
//                    }
                }
            });
        }
    }
}
