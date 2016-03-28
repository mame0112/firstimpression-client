package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultDetailData;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.TrackingUtil;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class AnswerDetailFragment extends ImpressionBaseFragment {

    private static final String TAG = Constants.TAG + AnswerDetailFragment.class.getSimpleName();

    private QuestionResultDetailData mData;

    private TextView mDescriptionView;

    private TextView mChoiceAView;

    private TextView mChoiceBView;

    private TextView mMaleAView;

    private TextView mFemaleAView;

//    private TextView mGenderUnknownAView;

    private TextView mUnder10AView;

    private TextView mFrom10_20AView;

    private TextView mFrom20_30AView;

    private TextView mFrom30_40AView;

    private TextView mFrom40_50AView;

    private TextView mFrom50_60AView;

    private TextView mFrom60_70AView;

    private TextView mOver70AView;

    private TextView mMaleBView;

    private TextView mFemaleBView;

//    private TextView mGenderUnknownBView;

    private TextView mUnder10BView;

    private TextView mFrom10_20BView;

    private TextView mFrom20_30BView;

    private TextView mFrom30_40BView;

    private TextView mFrom40_50BView;

    private TextView mFrom50_60BView;

    private TextView mFrom60_70BView;

    private TextView mOver70BView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");
    }

    public void updateDetailData(QuestionResultDetailData data){
        mData = data;
        updateViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer_detail_fragment, container, false);

        createCommonViews(view);

        createGenderAViews(view);
        createGenderBViews(view);

        createAgeAViews(view);
        createAgeBViews(view);

        return view;
    }

    private void createCommonViews(View view){
        mDescriptionView = (TextView)view.findViewById(R.id.answer_detail_description);
        mChoiceAView = (TextView)view.findViewById(R.id.answer_detail_choice_a);
        mChoiceBView = (TextView)view.findViewById(R.id.answer_detail_choice_b);
    }

    private void createGenderAViews(View view){
        mMaleAView = (TextView)view.findViewById(R.id.answer_detail_male_a);
        mFemaleAView = (TextView)view.findViewById(R.id.answer_detail_female_a);
        //        mGenderUnknownAView = (TextView)view.findViewById(R.id);
    }

    private void createGenderBViews(View view){
        mMaleBView = (TextView)view.findViewById(R.id.answer_detail_male_b);
        mFemaleBView = (TextView)view.findViewById(R.id.answer_detail_female_b);
//        mGenderUnknownBView = (TextView)view.findViewById(R.id);
    }

    private void createAgeAViews(View view){
        mUnder10AView = (TextView)view.findViewById(R.id.answer_detail_under10_a);
        mFrom10_20AView = (TextView)view.findViewById(R.id.answer_detail_from10_20_a);
        mFrom20_30AView = (TextView)view.findViewById(R.id.answer_detail_from20_30_a);
        mFrom30_40AView = (TextView)view.findViewById(R.id.answer_detail_from30_40_a);
        mFrom40_50AView = (TextView)view.findViewById(R.id.answer_detail_from40_50_a);
        mFrom50_60AView = (TextView)view.findViewById(R.id.answer_detail_from50_60_a);
        mFrom60_70AView = (TextView)view.findViewById(R.id.answer_detail_from60_70_a);
        mOver70AView = (TextView)view.findViewById(R.id.answer_detail_over70_a);
    }

    private void createAgeBViews(View view){
        mUnder10BView = (TextView)view.findViewById(R.id.answer_detail_under10_b);
        mFrom10_20BView = (TextView)view.findViewById(R.id.answer_detail_from10_20_b);
        mFrom20_30BView = (TextView)view.findViewById(R.id.answer_detail_from20_30_b);
        mFrom30_40BView = (TextView)view.findViewById(R.id.answer_detail_from30_40_b);
        mFrom40_50BView = (TextView)view.findViewById(R.id.answer_detail_from40_50_b);
        mFrom50_60BView = (TextView)view.findViewById(R.id.answer_detail_from50_60_b);
        mFrom60_70BView = (TextView)view.findViewById(R.id.answer_detail_from60_70_b);
        mOver70BView = (TextView)view.findViewById(R.id.answer_detail_over70_b);
    }

    private void updateViews(){
        LogUtil.d(TAG, "updateViews");

        mDescriptionView.setText(mData.getDescription());
        mChoiceAView.setText(mData.getChoiceA());
        mChoiceBView.setText(mData.getChoiceB());

        mDescriptionView.invalidate();
        mChoiceAView.invalidate();
        mChoiceBView.invalidate();

        mMaleAView.setText(String.valueOf(mData.getChoiceAItem().getMale()));
        mFemaleAView.setText(String.valueOf(mData.getChoiceAItem().getFemale()));
        mMaleBView.setText(String.valueOf(mData.getChoiceBItem().getMale()));
        mFemaleBView.setText(String.valueOf(mData.getChoiceBItem().getFemale()));

        mMaleAView.invalidate();
        mFemaleAView.invalidate();
        mMaleBView.invalidate();
        mFemaleBView.invalidate();

        mUnder10AView.setText(String.valueOf(mData.getChoiceAItem().getUnder10()));
        mFrom10_20AView.setText(String.valueOf(mData.getChoiceAItem().getFrom10_20()));
        mFrom20_30AView.setText(String.valueOf(mData.getChoiceAItem().getFrom20_30()));
        mFrom30_40AView.setText(String.valueOf(mData.getChoiceAItem().getFrom30_40()));
        mFrom40_50AView.setText(String.valueOf(mData.getChoiceAItem().getFrom40_50()));
        mFrom50_60AView.setText(String.valueOf(mData.getChoiceAItem().getFrom50_60()));
        mFrom60_70AView.setText(String.valueOf(mData.getChoiceAItem().getFrom60_70()));
        mOver70AView.setText(String.valueOf(mData.getChoiceAItem().getFrom60_70()));

        mUnder10AView.invalidate();
        mFrom10_20AView.invalidate();
        mFrom20_30AView.invalidate();
        mFrom30_40AView.invalidate();
        mFrom40_50AView.invalidate();
        mFrom50_60AView.invalidate();
        mFrom60_70AView.invalidate();
        mOver70AView.invalidate();

        mUnder10BView.setText(String.valueOf(mData.getChoiceBItem().getUnder10()));
        mFrom10_20BView.setText(String.valueOf(mData.getChoiceBItem().getFrom10_20()));
        mFrom20_30BView.setText(String.valueOf(mData.getChoiceBItem().getFrom20_30()));
        mFrom30_40BView.setText(String.valueOf(mData.getChoiceBItem().getFrom30_40()));
        mFrom40_50BView.setText(String.valueOf(mData.getChoiceBItem().getFrom40_50()));
        mFrom50_60BView.setText(String.valueOf(mData.getChoiceBItem().getFrom50_60()));
        mFrom60_70BView.setText(String.valueOf(mData.getChoiceBItem().getFrom60_70()));
        mOver70BView.setText(String.valueOf(mData.getChoiceBItem().getFrom60_70()));

        mUnder10BView.invalidate();
        mFrom10_20BView.invalidate();
        mFrom20_30BView.invalidate();
        mFrom30_40BView.invalidate();
        mFrom40_50BView.invalidate();
        mFrom50_60BView.invalidate();
        mFrom60_70BView.invalidate();
        mOver70BView.invalidate();

    }

    @Override
    protected void enterPage() {
        TrackingUtil.getInstance().trackPage(AnswerDetailFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }
}
