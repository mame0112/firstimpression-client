package com.mame.impression.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionLocalDataHandler {

    private final static String TAG = Constants.TAG + ImpressionLocalDataHandler.class.getSimpleName();

    private static SQLiteDatabase sDatabase;

    private static ImpressionLocalDataHandler sInstance = new ImpressionLocalDataHandler();

    private ImpressionLocalDataHandler(){
        // Singletone
    }

    public static ImpressionLocalDataHandler getInstance(){
        return sInstance;
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


//        String selection = DatabaseDef.CreatedQuestionColumns.FROM_USER_ID + "=?";
//        String selectionArgs[] = { String.valueOf(userId)};
        String selection = null;
        String selectionArgs[] = null;
//        String sortOrder = DatabaseDef.CreatedQuestionColumns.DATE + " DESC LIMIT "
//                + LcomConst.ITEM_ON_SCREEN;
        String sortOrder = null;
        String groupBy = null;
        String having = null;
//        cursor = context.getContentResolver().query(DatabaseDef.CreatedQuestionTable.URI, null,
//                selection, selectionArgs, sortOrder);
        cursor = sDatabase.query(DatabaseDef.CreatedQuestionTable.URI.toString(), null,
                selection, selectionArgs, groupBy, having, sortOrder);
        while (cursor != null && cursor.moveToNext()) {
            long questionId = cursor
                    .getLong(cursor
                            .getColumnIndex(DatabaseDef.CreatedQuestionColumns.QUESTION_ID));
            LogUtil.d(TAG, "questionId: " + questionId);
            ids.add(questionId);
        }

        return ids;
    }

    public synchronized void storeCreatedQuestionId(Context context, long questionId){

        LogUtil.d(TAG, "storeCreatedQuestionId");

        if(questionId == Constants.NO_QUESTION){
            throw new IllegalArgumentException("Question id cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        try {
            setDatabase(context);
            sDatabase.beginTransaction();

            ContentValues questionValues = getContentValueForCreatedQuestion(questionId);

            long id = sDatabase.insert(DatabaseDef.CreatedQuestionTable.TABLE_NAME, null, questionValues);
            LogUtil.d(TAG, "id: " + id);
            if(id < 0){
                LogUtil.d(TAG, "Failed to insert data for question");
            }

            sDatabase.setTransactionSuccessful();

        } catch (SQLException e){
            LogUtil.d(TAG, "SQLException: " + e.getMessage());
        } finally {
            if(sDatabase != null){
                sDatabase.endTransaction();
            }
        }
    }

    public synchronized void storeRespondedQuestionId(Context context, long questionId){

        LogUtil.d(TAG, "storeRespondedQuestionId");

        if(questionId == Constants.NO_QUESTION){
            throw new IllegalArgumentException("Question id cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        try {
            setDatabase(context);
            sDatabase.beginTransaction();

            ContentValues questionValues = getContentValueForRespondedQuestion(questionId);

            long id = sDatabase.insert(DatabaseDef.RespondedQuestionTable.TABLE_NAME, null, questionValues);
            LogUtil.d(TAG, "id: " + id);
            if(id < 0){
                LogUtil.d(TAG, "Failed to insert data for question");
            }

            sDatabase.setTransactionSuccessful();

        } catch (SQLException e){
            LogUtil.d(TAG, "SQLException: " + e.getMessage());
        } finally {
            if(sDatabase != null){
                sDatabase.endTransaction();
            }
        }

    }

    public synchronized boolean isQuestionAlreadyResponed(Context context, long questionId){

        if(questionId == Constants.NO_QUESTION){
            throw new IllegalArgumentException("question id cannot be null");
        }

        setDatabase(context);

        String selection = DatabaseDef.RespondedQuestionColumns.QUESTION_ID + " = ?";
        String selectionArgs[] = { String.valueOf(questionId)};
        String sortOrder = null;
        String groupBy = null;
        String having = null;
        Cursor cursor = sDatabase.query(DatabaseDef.RespondedQuestionTable.TABLE_NAME, null,
                selection, selectionArgs, groupBy, having, sortOrder);
        if(cursor != null && cursor.moveToFirst()){
//            while (cursor.moveToNext()) {
//                long id = cursor
//                        .getLong(cursor
//                                .getColumnIndex(DatabaseDef.CreatedQuestionColumns.QUESTION_ID));
//                LogUtil.d(TAG, "id: " + id);
//            }
            return true;

        }

        return false;
    }

    /**
     * We might want to manage point on Database. Then, we handle point information in this class.
     * @param context
     * @param diff
     * @return updated point.
     */
    public synchronized int updateUserPoint(Context context, int diff){
        LogUtil.d(TAG, "updateUserPoint: " + diff);

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        int current = PreferenceUtil.getUserPoint(context);
        int newPoint = current + diff;

        PreferenceUtil.setUserPoint(context, newPoint);

        return newPoint;

    }

    public synchronized int getUserPoint(Context context){
        LogUtil.d(TAG, "getUserPoint");

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        return PreferenceUtil.getUserPoint(context);

    }

    public synchronized void removeUserData(Context context){
        LogUtil.d(TAG, "removeUserData");

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        // Reset user point
        PreferenceUtil.setUserPoint(context, Constants.NO_POINT);

        // Clear database
        context.deleteDatabase(DatabaseDef.DATABASE_NAME);

    }

    protected ContentValues getContentValueForCreatedQuestion(long questionId){
        ContentValues values = new ContentValues();
        values.put(DatabaseDef.CreatedQuestionColumns.QUESTION_ID, questionId);
        return values;
    }

    protected ContentValues getContentValueForRespondedQuestion(long questionId){
        ContentValues values = new ContentValues();
        values.put(DatabaseDef.RespondedQuestionColumns.QUESTION_ID, questionId);
        return values;
    }

}
