package com.mame.impression.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mame.impression.R;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class NotificationDialogFragment extends DialogFragment {
    int mNum;

    public static NotificationDialogFragment newInstance(int num) {
        NotificationDialogFragment f = new NotificationDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.prompt_dialog_fragment, container, false);

        Button cancelButton = (Button)v.findViewById(R.id.prompt_dialog_cancel);
        cancelButton.setOnClickListener(mClickListener);

        Button acceptButton = (Button)v.findViewById(R.id.prompt_dialog_accept);
        acceptButton.setOnClickListener(mClickListener);

        return v;
    }

    private View.OnClickListener mClickListener= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.prompt_dialog_accept:
                    break;
                case R.id.prompt_dialog_cancel:
                    break;
                default:
                    break;
            }
        }
    };

}