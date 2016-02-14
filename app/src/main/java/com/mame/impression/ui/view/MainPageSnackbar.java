package com.mame.impression.ui.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.point.PointManager;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/02/07.
 */
public class MainPageSnackbar {

    private final static String TAG = Constants.TAG + MainPageSnackbar.class.getSimpleName();

    private ViewGroup mRootViewGroup;

    private Context mContext;

    private MainPageSnackbarListener mListener;

    public MainPageSnackbar(Context context, ViewGroup rootViewGroup){

        if(rootViewGroup == null){
            throw new IllegalArgumentException("Rootview cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        mContext = context;
        mRootViewGroup = rootViewGroup;
    }

    public void updateStatus(int point){
        LogUtil.d(TAG, "updateStatus");

        if(mListener == null){
            throw new IllegalArgumentException("setMainPageSnackbarListener must be called first");
        }

        if(PointManager.isEnoughPointForCreateNewQuestion(point)){
            showPointWithCreateQuestionActon(point);
        } else {
            showPoint(point);
        }
    }

    public void updateStatusWithError(int point){
        LogUtil.d(TAG, "updateStatusWithError");

        if(mListener == null){
            throw new IllegalArgumentException("setMainPageSnackbarListener must be called first");
        }

        Snackbar.make(mRootViewGroup, String.format(mContext.getString(R.string.main_page_not_enough_point_to_create_question), point), Snackbar.LENGTH_LONG).show();
    }

    private void showPoint(int point){

        int pt;

        if(point >= 0){
            pt = point;
        } else {
            pt = 0;
        }
        Snackbar.make(mRootViewGroup, String.format(mContext.getString(R.string.main_page_current_point), pt), Snackbar.LENGTH_SHORT).show();
    }

    private void showPointWithCreateQuestionActon(int point){

        Snackbar.make(mRootViewGroup, String.format(mContext.getString(R.string.main_page_current_point), point),
                Snackbar.LENGTH_SHORT).setAction(mContext.getString(R.string.main_pgae_create_question), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onCreateNewQuestionPressed();
                }
            }
        }).show();
    }

    public void setMainPageSnackbarListener(MainPageSnackbarListener listener){
        mListener = listener;
    }

    public interface MainPageSnackbarListener{
        void onCreateNewQuestionPressed();
    }



}
