package com.mame.impression.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.SignUpInPageActivity;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.view.SimpleSignUpFragment;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

import org.w3c.dom.Text;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class NotificationDialogFragment extends ImpressionBaseFragment implements SimpleSignUpFragment.SimpleSignUpFragmentListener {

    private final static String TAG = Constants.TAG + NotificationDialogFragment.class.getSimpleName();

    private SimpleSignUpFragment mSignUpFragment = new SimpleSignUpFragment();

    private final static String NOTIFICATION_FRAGMENT_TAG ="notification_fragment_tag";

    private NotificationDialogFragmentListener mListener;

    private TextView mAccountExistView;

//    public static NotificationDialogFragment newInstance() {
//        NotificationDialogFragment f = new NotificationDialogFragment();
//
//        // Supply num input as an argument.
////        Bundle args = new Bundle();
////        args.putInt("num", num);
////        f.setArguments(args);
//
//        return f;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpFragment.setSimpleSignUpFragmentListener(this);

//        getSupportFragmentManager().beginTransaction().replace(R.id.promot_layout_frame, mNotificationDialogFragment, DIALOG_TAG)
//                .commit();
//        mNum = getArguments().getInt("num");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_dialog_fragment, container, false);

        mAccountExistView = (TextView)v.findViewById(R.id.prompt_dialog_already_account_exist);
        mAccountExistView.setOnClickListener(mClickListener);

        getChildFragmentManager().beginTransaction().add(R.id.notification_fragment_frame, mSignUpFragment, NOTIFICATION_FRAGMENT_TAG).commit();

//        Button cancelButton = (Button)v.findViewById(R.id.prompt_dialog_cancel);
//        cancelButton.setOnClickListener(mClickListener);
//
//        Button acceptButton = (Button)v.findViewById(R.id.prompt_dialog_accept);
//        acceptButton.setOnClickListener(mClickListener);

//        getDialog().setTitle(R.string.notification_dialog_title);
//        getDialog().setCanceledOnTouchOutside(false);
//        setCancelable(false);

        return v;
    }

    @Override
    protected void enterPage() {
        AnalyticsTracker.getInstance().trackPage(NotificationDialogFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    private View.OnClickListener mClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.prompt_dialog_already_account_exist:
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_NOTIFICATION_DIALOG, AnalyticsTracker.EVENT_ACTION_NOTIFICATION_BUTTON, AnalyticsTracker.EVENT_LABEL_NOTIFICATION_SIGNIN_BUTTON, 0);
                    if(mListener != null){
                        mListener.onNotificationSigninButtonPressed();
                    }
                    break;
            }
        }
    };

    @Override
    public void onStart(){
        super.onStart();
    }

//    private View.OnClickListener mClickListener= new View.OnClickListener(){
//        @Override
//        public void onClick(View v) {
//            switch(v.getId()){
//                case R.id.prompt_dialog_accept:
//                    if(mListener != null){
//                        mListener.onNotificationSigninButtonPressed();
//                    }
//                    break;
//                case R.id.prompt_dialog_cancel:
//                    if(mListener != null) {
//                        mListener.onNotificationCancelButtonPressed();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    public void setNotificationDialogFragmentListener(NotificationDialogFragmentListener listener){
        mListener = listener;
    }

    @Override
    public void onSignUpButtonPressed(String userName, String password) {
        LogUtil.d(TAG, "onSignUpButtonPressed");
        if(mListener != null){
            mListener.onSignUpButtonPressed(userName, password);
        }
    }


    public interface NotificationDialogFragmentListener{
        void onNotificationSigninButtonPressed();

        void onNotificationCancelButtonPressed();

        void onSignUpButtonPressed(String userName, String password);
    }

}