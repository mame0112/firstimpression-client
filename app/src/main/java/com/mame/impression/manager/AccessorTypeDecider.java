package com.mame.impression.manager;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.server.ServerAccessor;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class AccessorTypeDecider {

    private static final String TAG = Constants.TAG + AccessorTypeDecider.class.getSimpleName();

    public static List<Accessor> createAccessor(RequestAction param) {
        LogUtil.d(TAG, "createAccessor");

        List<Accessor> accessors = new ArrayList<Accessor>();

        switch (param) {
            case GET_QUESTION_LIST:
                accessors.add(new ServerAccessor());
                return accessors;
        }

        return null;

    }

}
