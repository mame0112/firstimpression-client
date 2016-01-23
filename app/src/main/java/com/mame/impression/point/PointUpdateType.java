package com.mame.impression.point;

import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2016/01/23.
 */
public enum PointUpdateType {
    CREATE_NEW_QUESTION,
    RESPOND_TO_QUESTION,
    ADD_PROFILE;

    private final static int UP_MINIMUM = 3;

    private final static int UP_MEDIUM = 5;

    private final static int UP_MAXIMUM = 10;

    private final static int DOWN = -5;

    public static int getPointChangeValue(PointUpdateType type){

        switch(type){
            case CREATE_NEW_QUESTION:
                return DOWN;
            case RESPOND_TO_QUESTION:
                return UP_MINIMUM;
            case ADD_PROFILE:
                return UP_MAXIMUM;

        }

        return Constants.NO_POINT;
    }
}
