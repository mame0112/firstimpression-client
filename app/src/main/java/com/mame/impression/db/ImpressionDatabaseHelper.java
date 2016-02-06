package com.mame.impression.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = Constants.TAG + ImpressionDatabaseHelper.class.getSimpleName();

    static final String CREATED_QUESTION_DATA_SQL = "CREATE TABLE IF NOT EXISTS "
            + DatabaseDef.CreatedQuestionTable.TABLE_NAME + " ("
            + DatabaseDef.CreatedQuestionColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseDef.CreatedQuestionColumns.QUESTION_ID + " INTEGER DEFAULT 0)";

    static final String RESPONDED_QUESTION_DATA_SQL = "CREATE TABLE IF NOT EXISTS "
            + DatabaseDef.RespondedQuestionTable.TABLE_NAME + " ("
            + DatabaseDef.RespondedQuestionColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseDef.RespondedQuestionColumns.QUESTION_ID + " INTEGER DEFAULT 0)";

    public ImpressionDatabaseHelper(Context context) {
        super(context, DatabaseDef.DATABASE_NAME, null, DatabaseDef.DATABASE_VERSION);

        LogUtil.d(TAG, "ImpressionDatabaseHelper constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.d(TAG, "onCreate");

        try {
            db.execSQL(CREATED_QUESTION_DATA_SQL);
            db.execSQL(RESPONDED_QUESTION_DATA_SQL);
        } catch (SQLException e) {
            LogUtil.d(TAG, "SQLException: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d(TAG, "onUpgrade");
    }
}
