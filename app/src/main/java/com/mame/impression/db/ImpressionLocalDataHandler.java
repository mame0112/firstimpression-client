package com.mame.impression.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionLocalDataHandler {

    private final static String TAG = Constants.TAG + ImpressionLocalDataHandler.class.getSimpleName();

    private static SQLiteDatabase sDatabase;

    public ImpressionLocalDataHandler(){
        LogUtil.d(TAG, "ImpressionLocalDataHandler");
    }

    private synchronized void setDatabase(Context context) {
        if (sDatabase == null || !sDatabase.isOpen()) {
            ImpressionDatabaseHelper helper = new ImpressionDatabaseHelper(context);
//            sDatabase.loadLibs(mContext);
//            String UUID = SecurityUtil.getUniqueId(context);
            sDatabase = helper.getWritableDatabase();
        }
    }

    public synchronized List<Long> getQuestionIdCreatedByUser(Context context, long userId){
        LogUtil.d(TAG, "storeQuestionIdCreatedByUser");

        if(userId == Constants.NO_USER){
            throw new IllegalArgumentException("user id cannot be null");
        }

        setDatabase(context);

        Cursor cursor = null;

        List<Long> ids = new ArrayList<Long>();


//        String selection = DatabaseDef.QuestionColumns.FROM_USER_ID + "=?";
//        String selectionArgs[] = { String.valueOf(userId)};
        String selection = null;
        String selectionArgs[] = null;
//        String sortOrder = DatabaseDef.QuestionColumns.DATE + " DESC LIMIT "
//                + LcomConst.ITEM_ON_SCREEN;
        String sortOrder = null;
        cursor = context.getContentResolver().query(DatabaseDef.QuestionTable.URI, null,
                selection, selectionArgs, sortOrder);
        while (cursor != null && cursor.moveToNext()) {
            long questionId = cursor
                    .getLong(cursor
                            .getColumnIndex(DatabaseDef.QuestionColumns.QUESTION_ID));
            LogUtil.d(TAG, "questionId: " + questionId);
            ids.add(questionId);
        }

        return ids;
    }

    public synchronized void storeQuestionId(Context context, long questionId){

        LogUtil.d(TAG, "storeQuestionId");

        if(questionId == Constants.NO_QUESTION){
            throw new IllegalArgumentException("Question id cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        try {
            setDatabase(context);
            sDatabase.beginTransaction();

            ContentValues questionValues = getContentValueForQuestion(questionId);

            long id = sDatabase.insert(DatabaseDef.QuestionTable.TABLE_NAME, null, questionValues);
            LogUtil.d(TAG, "id: " + id);
            if(id < 0){
                LogUtil.d(TAG, "Failed to insert data for question");
            }

        } catch (SQLException e){
            LogUtil.d(TAG, "SQLException: " + e.getMessage());
        }

    }

    protected ContentValues getContentValueForQuestion(long questionId){
        ContentValues values = new ContentValues();
        values.put(DatabaseDef.QuestionColumns.QUESTION_ID, questionId);
        return values;
    }


}
