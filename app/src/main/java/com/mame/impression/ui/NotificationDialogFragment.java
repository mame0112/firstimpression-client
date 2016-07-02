package com.mame.impression.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.view.SimpleSignUpFragment;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class NotificationDialogFragment extends ImpressionBaseFragment implements SimpleSignUpFragment.SimpleSignUpFragmentListener {

    private final static String TAG = Constants.TAG + NotificationDialogFragment.class.getSimpleName();

    private SimpleSignUpFragment mSignUpFragment = new SimpleSignUpFragment();

    private final static String NOTIFICATION_FRAGMENT_TAG ="notification_fragment_tag";

    private NotificationDialogFragmentListener mListener;

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
        View v = inflater.inflate(R.layout.prompt_dialog_fragment, container, false);
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

    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onStart(){
        super.onStart();
        AnalyticsTracker.getInstance().trackPage(NotificationDialogFragment.class.getSimpleName());
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