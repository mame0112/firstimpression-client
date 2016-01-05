package com.mame.impression.util;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.UserData;
import com.mame.impression.data.UserDataBuilder;
import com.mame.impression.ui.MainPageContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/05.
 */
public class JSONParser {

    private static final String TAG = Constants.TAG + JSONParser.class.getSimpleName();

    public MainPageContent createMainPageContent(JSONObject object){

        try {
            long createUserId = object.getLong(JsonParam.QUESTION_CREATED_USER_ID);
            int choiceAResponse= object.getInt(JsonParam.QUESTION_CHOICE_A_RESPONSE);
            long questionId = object.getLong(JsonParam.QUESTION_ID);
            String description = object.getString(JsonParam.QUESTION_DESCRIPTION);
            String additionalQuestion = object.getString(JsonParam.QUESTION_ADDITIONAL_QUESTION);
            List<String> additionalComment = (List<String>)object.get(JsonParam.QUESTION_ADDITIONAL_COMMENT);

            //TODO
            return new MainPageContent(questionId, null, String.valueOf(questionId), null, description, null, null);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

//        public final static String QUESTION_CATEGORY = "category";
//        public final static String QUESTION_CHOICE = "choice";
//        public final static String QUESTION_CHOICE_A = "choice_a";
//        public final static String QUESTION_CHOICE_B = "choice_b";
//        public final static String QUESTION_CREATED_USER_NAME = "created_user_name";
//        public final static String QUESTION_THUMBNAIL = "thumbnail";
//        public final static String QUESTION_CHOICE_B_RESPONSE = "choice_b_response";

        return null;
    }

    public UserData createUserData(JSONObject object){

        if(object != null){
            try {
                JSONObject paramObject = extractParamObject(object);

                long userId = paramObject.getLong(JsonParam.USER_ID);
                String userName = paramObject.getString(JsonParam.USER_NAME);
                String password = paramObject.getString(JsonParam.USER_PASSWORD);
                JSONArray jsonArray = (JSONArray)paramObject.get(JsonParam.USER_CREATED_QUESTION_ID);

                List<Long> list = new ArrayList<Long>();
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i=0;i<len;i++){
                        list.add((Long)jsonArray.get(i));
                    }
                }

                UserDataBuilder builder = new UserDataBuilder();
                return builder.setUserId(userId).setUserName(userName).setPassword(password).setCreatedQuestionIds(list).getResult();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

    private JSONObject extractParamObject(JSONObject input){

        if(input != null){
            try {
                return (JSONObject)input.get(JsonParam.PARAM);
            } catch (JSONException e) {
                LogUtil.w(TAG, "JSONException: " + e.getMessage());
            }
        }

        return null;
    }
}
