package com.mame.impression.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.ui.view.ButtonUtil;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.TrackingUtil;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class SignUpPageFragment extends ImpressionBaseFragment {

    private static final String TAG = Constants.TAG + SignUpPageFragment.class.getSimpleName();

    private Button mSignUpButton;

    private EditText mUserNameView;

    private EditText mPasswordView;

    private TextView mToSView;

    private Spinner mAgeSpinner;

    private Spinner mGenderSpinner;

    private TextView mPricacyView;

    private String mUserName;

    private String mPassword;

    private int mGenderSelectedId = R.string.spinner_gender_select_indication;

    private int mAgeSelectedId = R.string.spinner_age_select_indication;

    private SignUpFragmentListener mListener;

    private static int AGE_ITEM[] = {
        R.string.spinner_gender_select_indication,
        R.string.item_generation_under10,
        R.string.item_generation_from10_20,
        R.string.item_generation_from20_30,
        R.string.item_generation_from30_40,
        R.string.item_generation_from40_50,
        R.string.item_generation_from50_60,
        R.string.item_generation_from60_70,
        R.string.item_generation_over70
    };

    private static int GENDER_ITEM[] = {
            R.string.spinner_age_select_indication,
            R.string.item_gender_male,
            R.string.item_gender_female
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        mUserNameView = (EditText) view.findViewById(R.id.signup_username);
        mUserNameView.addTextChangedListener(mUserNmaeWatcher);

        mPasswordView = (EditText) view.findViewById(R.id.signup_password);
        mPasswordView.addTextChangedListener(mPasswordWatcher);

        mSignUpButton = (Button) view.findViewById(R.id.signup_button);
        mSignUpButton.setOnClickListener(mClickListener);

        // Age spinner
        mAgeSpinner = (Spinner)view.findViewById(R.id.signup_age_spinner);
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        for(int i=0; i<AGE_ITEM.length; i++){
            ageAdapter.add(getResources().getString(AGE_ITEM[i]));
        }
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgeSpinner.setAdapter(ageAdapter);
        mAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAgeSelectedId = AGE_ITEM[position];
                changeSignUpButtonState();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Gender spinner
        mGenderSpinner = (Spinner)view.findViewById(R.id.signup_gender_spinner);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        for(int i=0; i<GENDER_ITEM.length; i++){
            genderAdapter.add(getResources().getString(GENDER_ITEM[i]));
        }
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(genderAdapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGenderSelectedId = GENDER_ITEM[position];
                changeSignUpButtonState();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mToSView = (TextView)view.findViewById(R.id.signup_tos);
        mToSView.setOnClickListener(mClickListener);

        mPricacyView = (TextView)view.findViewById(R.id.signup_privacy_policy);
        mPricacyView.setOnClickListener(mClickListener);

        return view;
    }

    private TextWatcher mUserNmaeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mUserName = s.toString();
            changeSignUpButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher mPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mPassword = s.toString();
            changeSignUpButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_button:
                    LogUtil.d(TAG, "signup button pressed");
                    if(ButtonUtil.isClickable()){
                        TrackingUtil.trackEvent(getActivity(), TrackingUtil.EVENT_CATEGORY_SIGNUP, TrackingUtil.EVENT_ACTION_SIGNUP_BUTTON, TrackingUtil.EVENT_CATEGORY_SIGNUP_BUTTON, 0);
                        QuestionResultListData.Gender gender = getGenderValue();
                        QuestionResultListData.Age age = getAgeValue();
                        mListener.onSignUpButtonPressed(mUserName, mPassword, gender, age);
                    }
                    break;
                case R.id.signup_tos:
                    LogUtil.d(TAG, "TOS");
                    TrackingUtil.trackEvent(getActivity(), TrackingUtil.EVENT_CATEGORY_SIGNUP, TrackingUtil.EVENT_ACTION_SIGNUP_BUTTON, TrackingUtil.EVENT_CATEGORY_SIGNIN_TOS, 0);
                    openTosPage();
                    break;
                case R.id.signup_privacy_policy:
                    LogUtil.d(TAG, "Privacy policy");
                    TrackingUtil.trackEvent(getActivity(), TrackingUtil.EVENT_CATEGORY_SIGNUP, TrackingUtil.EVENT_ACTION_SIGNUP_BUTTON, TrackingUtil.EVENT_CATEGORY_SIGNIN_PRIVACY, 0);
                    openPrivacyPolicyPage();
                    break;

                default:
                    break;
            }
        }
    };

    private QuestionResultListData.Gender getGenderValue(){
        switch(mGenderSelectedId){
            case R.string.spinner_age_select_indication:
                break;
            case R.string.item_gender_male:
                return QuestionResultListData.Gender.MALE;
            case R.string.item_gender_female:
                return QuestionResultListData.Gender.FEMALE;
            default:
                break;
        }
        return QuestionResultListData.Gender.UNKNOWN;
    }

    private QuestionResultListData.Age getAgeValue(){
        switch(mAgeSelectedId){
            case R.string.spinner_gender_select_indication:
                break;
            case R.string.item_generation_under10:
                return QuestionResultListData.Age.UNDER10;
            case R.string.item_generation_from10_20:
                return QuestionResultListData.Age.FROM10_20;
            case R.string.item_generation_from20_30:
                return QuestionResultListData.Age.FROM20_30;
            case R.string.item_generation_from30_40:
                return QuestionResultListData.Age.FROM30_40;
            case R.string.item_generation_from40_50:
                return QuestionResultListData.Age.FROM40_50;
            case R.string.item_generation_from50_60:
                return QuestionResultListData.Age.FROM50_60;
            case R.string.item_generation_from60_70:
                return QuestionResultListData.Age.FROM60_70;
            case R.string.item_generation_over70:
                return QuestionResultListData.Age.OVER70;
            default:
                break;
        }

        return QuestionResultListData.Age.FROM10_20;
    }

    private void openTosPage(){
        Uri uri = Uri.parse(Constants.TOS_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    private void openPrivacyPolicyPage(){
        Uri uri = Uri.parse(Constants.PRIVACY_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    private void changeSignUpButtonState(){
        if(isValidButtonState()){
            mSignUpButton.setEnabled(true);
        } else {
            mSignUpButton.setEnabled(false);
        }
    }

    private boolean isValidButtonState(){
        if(mUserName != null && mUserName.length() >= Constants.USERNAME_MIN_LENGTH && mUserName.length() <= Constants.USERNAME_MAX_LENGTH){
            if(mPassword != null && mPassword.length() >= Constants.PASSWORD_MIN_LENGTH && mPassword.length() <= Constants.PASSWORD_MAX_LENGTH){
                if(mAgeSelectedId != R.string.spinner_gender_select_indication){
                    if(mGenderSelectedId != R.string.spinner_age_select_indication){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void setSignUpFragmentListener(SignUpFragmentListener listener){
        mListener = listener;
    }

    @Override
    protected void enterPage() {
        TrackingUtil.trackPage(getActivity(), SignUpPageFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    public interface SignUpFragmentListener{
        void onSignUpButtonPressed(String userName, String password, QuestionResultListData.Gender gender, QuestionResultListData.Age age);
    }

}
