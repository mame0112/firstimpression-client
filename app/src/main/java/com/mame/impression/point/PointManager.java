package com.mame.impression.point;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/02/07.
 */
public class PointManager {

    private final static String TAG = Constants.TAG + PointManager.class.getSimpleName();

    public static boolean isEnoughPointForCreateNewQuestion(int currentPoint){

        LogUtil.d(TAG, "isEnoughPointForCreateNewQuestion");

        if(currentPoint + PointUpdateType.getPointChangeValue(PointUpdateType.CREATE_NEW_QUESTION) >= 0){
            return true;
        }

        return false;
    }

}
