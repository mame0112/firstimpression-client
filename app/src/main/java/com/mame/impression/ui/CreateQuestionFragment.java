package com.mame.impression.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.view.ButtonUtil;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.AnalyticsTracker;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateQuestionFragment extends ImpressionBaseFragment {

    private final static String TAG = Constants.TAG + CreateQuestionFragment.class.getSimpleName();

    private CreateQuestionFragmentListener mCreateQuestionListener;

    private TextInputLayout mDescriptionWrapper;

    private TextInputLayout mChoiceAEditWrapper;

    private TextInputLayout mChoiceBEditWrapper;

    private EditText mDescriptionView;

    private EditText mChoiceAEditView;

    private EditText mChoiceBEditView;

    private Button mCreateButton;

    private String mDescription;

    private String mChoiceAString;

    private String mChoiceBString;

    private DescriptionTextValidator mDescValidator;

    private ChoiceTextValidator mChoiceValidator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        mDescValidator = new DescriptionTextValidator();
        mChoiceValidator = new ChoiceTextValidator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_question_fragment, container, false);

        mDescriptionView = (EditText)view.findViewById(R.id.create_question_description);
        mDescriptionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDescription = s.toString();
                changeCreateButtonState();
                TextValidator.VALIDATION_RESULT result = mDescValidator.isValidInput(mDescription);
                showResultForDescription(result, mDescriptionWrapper);
            }
        });

        mDescriptionWrapper = (TextInputLayout)view.findViewById(R.id.create_question_description_wrapper);
        mDescriptionWrapper.setEnabled(true);

        mChoiceAEditView = (EditText)view.findViewById(R.id.create_question_choice_a);
        mChoiceAEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mChoiceAString = s.toString();
                changeCreateButtonState();
                TextValidator.VALIDATION_RESULT result = mChoiceValidator.isValidInput(mChoiceAString);
                showResultForChoice(result, mChoiceAEditWrapper);
//                mDescriptionWrapper.setError("Error!!");
            }
        });

        mChoiceAEditWrapper = (TextInputLayout)view.findViewById(R.id.create_question_choice_a_wrapper);
        mChoiceAEditWrapper.setEnabled(true);

        mChoiceBEditView = (EditText)view.findViewById(R.id.create_question_choice_b);
        mChoiceBEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mChoiceBString = s.toString();
                changeCreateButtonState();
                TextValidator.VALIDATION_RESULT result = mChoiceValidator.isValidInput(mChoiceBString);
                showResultForChoice(result, mChoiceBEditWrapper);
            }
        });

        mChoiceBEditWrapper = (TextInputLayout)view.findViewById(R.id.create_question_choice_b_wrapper);
        mChoiceBEditWrapper.setEnabled(true);

        mCreateButton = (Button)view.findViewById(R.id.create_question_create_button);
        mCreateButton.setEnabled(false);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCreateQuestionListener == null){
                    throw new IllegalStateException("Need to call setCreateQuestionFragmentListener first");
                }

                if(ButtonUtil.isClickable()){
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_CREATE_QUESTION, AnalyticsTracker.EVENT_ACTION_CREATE_QUESTION_BUTTON, AnalyticsTracker.EVENT_LABEL_CREATE_QUESTION_CREATE, 0);
                    mCreateButton.setEnabled(false);
                    mCreateQuestionListener.onCreateButtonPressed(mDescription, mChoiceAString, mChoiceBString);
                }

            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mCreateButton != null){
            changeCreateButtonState();
        }

    }

    public void changeCreateButtonState(){
        if(mDescription != null && mChoiceAString != null && mChoiceBString != null){
            if(mDescription.length() >= Constants.DESCRIPTION_MIN_LENGTH && mDescription.length() <= Constants.DESCRIPTION_MAX_LENGTH){
                if(mChoiceAString.length() >= Constants.DESCRIPTION_CHOICE_MIN_LENGTH && mChoiceAString.length() <= Constants.DESCRIPTION_CHOICE_MAX_LENGTH){
                    if(mChoiceBString.length() >= Constants.DESCRIPTION_CHOICE_MIN_LENGTH && mChoiceBString.length() <= Constants.DESCRIPTION_CHOICE_MAX_LENGTH){
                        mCreateButton.setEnabled(true);
                    } else {
                        mCreateButton.setEnabled(false);
                    }
                } else {
                    mCreateButton.setEnabled(false);
                }
            } else {
                mCreateButton.setEnabled(false);
            }
        } else {
            mCreateButton.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setCreateQuestionFragmentListener(CreateQuestionFragmentListener listener){
        mCreateQuestionListener = listener;
    }

    @Override
    protected void enterPage() {
        AnalyticsTracker.getInstance().trackPage(CreateQuestionFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    public interface CreateQuestionFragmentListener{
        void onCreateButtonPressed(String description, String choiceA, String choiceB);
    }

    private void showResultForDescription(TextValidator.VALIDATION_RESULT result, TextInputLayout inputLayout){
        if(result != null){
            switch(result){
                case RESULT_OK:
                    inputLayout.setError(null);
                    break;
                case INPUT_NULL:
                    inputLayout.setError(getString(R.string.str_create_question_error_desc_null));
                    break;
                case INPUT_SHORT:
                    inputLayout.setError(getString(R.string.str_create_question_error_desc_short));
                    break;
                case INPUT_LONG:
                    inputLayout.setError(getString(R.string.str_create_question_error_desc_long));
                    break;
                case INVALIDED_INPUT_CHAR_TYPE:
                    break;
            }
        }
    }

    private void showResultForChoice(TextValidator.VALIDATION_RESULT result, TextInputLayout inputLayout){
        if(result != null){
            switch(result){
                case RESULT_OK:
                    inputLayout.setError(null);
                    break;
                case INPUT_NULL:
                    inputLayout.setError(getString(R.string.str_create_question_error_choice_null));
                    break;
                case INPUT_SHORT:
                    inputLayout.setError(getString(R.string.str_create_question_error_choice_short));
                    break;
                case INPUT_LONG:
                    inputLayout.setError(getString(R.string.str_create_question_error_choice_long));
                    break;
                case INVALIDED_INPUT_CHAR_TYPE:
                    break;
            }
        }
    }

    public class DescriptionTextValidator extends TextValidator{

        @Override
        public int getMinimumInputength() {
            return Constants.DESCRIPTION_MIN_LENGTH;
        }

        @Override
        public int getMaximumInputength() {
            return Constants.DESCRIPTION_MAX_LENGTH;
        }

        @Override
        public String getAcceptedInputType() {
            return null;
        }
    }

    public  class ChoiceTextValidator extends TextValidator{

        @Override
        public int getMinimumInputength() {
            return Constants.DESCRIPTION_CHOICE_MIN_LENGTH;
        }

        @Override
        public int getMaximumInputength() {
            return Constants.DESCRIPTION_CHOICE_MAX_LENGTH;
        }

        @Override
        public String getAcceptedInputType() {
            return null;
        }
    }
}
