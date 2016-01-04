package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateQuestionFragment extends Fragment {

    private final static String TAG = Constants.TAG + CreateQuestionFragment.class.getSimpleName();

    private ImpressionService mService;

    private EditText mDescriptionView;

    private EditText mChoiceAEditView;

    private EditText mChoiceBEditView;

    private Button mCreateButton;

    private String mDescription;

    private String mChoiceAString;

    private String mChoiceBString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        mService = ImpressionService.getService(CreateQuestionFragment.class);
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
            }
        });

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
            }
        });

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
            }
        });

        mCreateButton = (Button)view.findViewById(R.id.create_question_create_button);
        mCreateButton.setEnabled(false);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "create button pressed");
            }
        });
        
        return view;
    }

    private void changeCreateButtonState(){
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

        mService.finalize(this.getClass());
    }
}
