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

    interface CreatedQuestionTable {
        String TABLE_NAME = "created_question";

        String MIME_TYPE = "impression-question";

        String QUESTION_PATH = "created_question";
//
//        static final String SINGLE_FRIENDSHIP_PATH = "friendship/#";

        static final Uri URI = Uri.withAppendedPath(BASE_URI, QUESTION_PATH);
    }

    public interface CreatedQuestionColumns extends BaseColumns{
        static final String QUESTION_ID = "created_question_id";
    }

    interface RespondedQuestionTable {
        String TABLE_NAME = "responded_question";

        String MIME_TYPE = "impression-question";

        String QUESTION_PATH = "responded_question";
//
//        static final String SINGLE_FRIENDSHIP_PATH = "friendship/#";

        static final Uri URI = Uri.withAppendedPath(BASE_URI, QUESTION_PATH);
    }

    public interface RespondedQuestionColumns extends BaseColumns{
        static final String QUESTION_ID = "responded_question_id";
    }

}
