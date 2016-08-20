package com.mame.impression.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class ProfileDialogFragment extends ImpressionBaseFragment {

    private final static String TAG = Constants.TAG + ProfileDialogFragment.class.getSimpleName();

    private int mGenderItem = 0;

    private int mAgeItem = 0;

    private Button mAnswerButton;

    private ProfileDialogFragmentListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_dialog_fragment, container, false);

        Spinner ageSpinner = (Spinner)v.findViewById(R.id.profile_age_spinner);
        ArrayAdapter<CharSequence> ageAdapter =
                ArrayAdapter.createFromResource(
                        getActivity(),
                        R.array.impression_age,
                        android.R.layout.simple_spinner_item
                );
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(mSpinnerListener);

        Spinner genderSpinner = (Spinner)v.findViewById(R.id.profile_gender_spinner);
        ArrayAdapter<CharSequence> genderAdapter =
                ArrayAdapter.createFromResource(
                        getActivity(),
                        R.array.impression_gender,
                        android.R.layout.simple_spinner_item
                );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(mSpinnerListener);

        mAnswerButton = (Button)v.findViewById(R.id.profile_answer_button);
        mAnswerButton.setOnClickListener(mClickListener);

//        getDialog().setTitle(R.string.notification_dialog_title);
//
//        getDialog().setCanceledOnTouchOutside(false);
//        setCancelable(false);

        return v;
    }

    @Override
    protected void enterPage() {
        AnalyticsTracker.getInstance().trackPage(ProfileDialogFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onStart(){
        super.onStart();
        AnalyticsTracker.getInstance().trackPage(ProfileDialogFragment.class.getSimpleName());
    }

    @Override
    public void onDestroy(){
        LogUtil.d(TAG, "onDestroy");
        super.onDestroy();
    }

    private AdapterView.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch(parent.getId()){
                case R.id.profile_age_spinner:
                    LogUtil.d(TAG, "Age onItemSelected: " + position);
                    mAgeItem = position;
                    changeAnswerButtonStatus();
                    break;
                case R.id.profile_gender_spinner:
                    LogUtil.d(TAG, "Gender onItemSelected: " + position);
                    mGenderItem = position;
                    changeAnswerButtonStatus();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            LogUtil.d(TAG, "onNothingSelected");
        }
    };

    private View.OnClickListener mClickListener= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.profile_answer_button:
                    LogUtil.d(TAG, "Answer button pressed");
                    if(mListener != null){
                        mListener.onProfileIsfulfilled(getGenderValue(mGenderItem), getAgeValue(mAgeItem));
                    }
                    break;
                case R.id.profile_cancel_button:
                    LogUtil.d(TAG, "Cancel button pressed");
                    if(mListener != null){
                        mListener.onProfileInputCancelled();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void changeAnswerButtonStatus(){
        if(mGenderItem != 0 && mAgeItem != 0){
            mAnswerButton.setEnabled(true);
        } else {
            mAnswerButton.setEnabled(false);
        }
    }

    public QuestionResultListData.Gender getGenderValue(int position) {
        switch(position){
            case 0: // Not selected
                return null;
            case 1: // Male
                return QuestionResultListData.Gender.MALE;
            case 2: // Female
                return QuestionResultListData.Gender.FEMALE;
//            case 3: // Unknown
//                return QuestionResultListData.Gender.UNKNOWN;
            default:
                return null;
        }
    }

    public QuestionResultListData.Age getAgeValue(int position) {
        switch (position) {
            case 0: // Not selected
                return null;
            case 1: // Under 10
                return QuestionResultListData.Age.UNDER10;
            case 2: // 10 - 20
                return QuestionResultListData.Age.FROM10_20;
            case 3: // 20 - 30
                return QuestionResultListData.Age.FROM20_30;
            case 4: // 30 - 40
                return QuestionResultListData.Age.FROM30_40;
            case 5: // 40 - 50
                return QuestionResultListData.Age.FROM40_50;
            case 6: // 50 - 60
                return QuestionResultListData.Age.FROM50_60;
            case 7: // 60 - 70
                return QuestionResultListData.Age.FROM60_70;
            case 8: // Over 70
                return QuestionResultListData.Age.OVER70;
            default:
                return null;
        }
    }

    public void setProfileDialogFragmentListener(ProfileDialogFragmentListener listener){
        mListener = listener;
    }

    public interface ProfileDialogFragmentListener{
        void onProfileInputCancelled();

        void onProfileIsfulfilled(QuestionResultListData.Gender gender, QuestionResultListData.Age age);
    }
}
