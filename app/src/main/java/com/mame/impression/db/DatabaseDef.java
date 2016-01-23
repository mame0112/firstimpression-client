package com.mame.impression.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class DatabaseDef {
    public static final String AUTHORITY = "com.mame.impression.db";

    protected static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    static final String DATABASE_NAME = DbConstant.DATABASE_NAME;

    static final int DATABASE_VERSION = 1;

    interface QuestionTable {
        String TABLE_NAME = "question";

        String MIME_TYPE = "impression-question";

        String QUESTION_PATH = "question";
//
//        static final String SINGLE_FRIENDSHIP_PATH = "friendship/#";

        static final Uri URI = Uri.withAppendedPath(BASE_URI, QUESTION_PATH);
    }

    public interface QuestionColumns extends BaseColumns{
        static final String QUESTION_ID = "question_id";
    }

    public interface Constants {
        static final int QUESTION_MATCH = 10;

        static final int POINT_MATCH = 20;
    }

}
