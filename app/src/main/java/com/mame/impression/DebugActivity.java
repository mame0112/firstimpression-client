package com.mame.impression;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mame.impression.constant.Constants;
import com.mame.impression.server.JsonConstants;
import com.mame.impression.server.WebApi;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kosukeEndo on 2015/12/20.
 */
public class DebugActivity extends Activity {

    private static final String TAG = Constants.TAG + DebugActivity.class.getSimpleName();

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);

        Button userGetButton = (Button)findViewById(R.id.debug_user_get);
        userGetButton.setOnClickListener(mClicklistener);

        Button userPostButton = (Button)findViewById(R.id.debug_user_post);
        userPostButton.setOnClickListener(mClicklistener);

        Button userPutButton = (Button)findViewById(R.id.debug_user_put);
        userPutButton.setOnClickListener(mClicklistener);

        Button userDeleteButton = (Button)findViewById(R.id.debug_user_delete);
        userDeleteButton.setOnClickListener(mClicklistener);

        mQueue = Volley.newRequestQueue(this);

    }

    private View.OnClickListener mClicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.debug_user_get:
                    LogUtil.d(TAG, "Get");
                    try {
                        JSONObject root = new JSONObject();
                        root.put(JsonConstants.USER_ID, 1);
                        root.put(JsonConstants.USER_NAME, "testname");
                        get(Constants.USER, root);
                    } catch (JSONException e) {
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    }
                    break;
                case R.id.debug_user_post:
                    LogUtil.d(TAG, "Post");
                    try {
                        JSONObject root = new JSONObject();
                        root.put(JsonConstants.USER_ID, 1);
                        root.put(JsonConstants.USER_NAME, "testname");
                        root.put(JsonConstants.USER_PASSWORD, "testpassword");
                        root.put(JsonConstants.USER_AGE, "10-20");
                        root.put(JsonConstants.USER_GENDER, "MALE");
                        root.put(JsonConstants.USER_THUMBNAIL, "http://xxxx");
                        root.put(JsonConstants.USER_CREATED_QUESTION_ID, new ArrayList<Long>());
                        post(Constants.USER, root);
                    } catch (JSONException e) {
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    }
                    break;
                case R.id.debug_user_put:
                    LogUtil.d(TAG, "Put");
                    try {
                        JSONObject root = new JSONObject();
                        root.put(JsonConstants.USER_ID, 1);
                        root.put(JsonConstants.USER_NAME, "testname");
                        put(Constants.USER, root);
                    } catch (JSONException e) {
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    }
                    break;
                case R.id.debug_user_delete:
                    LogUtil.d(TAG, "Delete");
                    try {
                        JSONObject root = new JSONObject();
                        root.put(JsonConstants.USER_ID, 1);
                        root.put(JsonConstants.USER_NAME, "testname");
                        delete(Constants.USER, root);
                    } catch (JSONException e) {
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void get(String api, final JSONObject input) {

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, Constants.HTTP_URL+api, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(JsonConstants.PARAM, input.toString());

                return params;
            }
        };

        mQueue.add(getRequest);
    }

    private void post(String api, final JSONObject input) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.HTTP_URL+api,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(JsonConstants.PARAM, input.toString());

                return params;
            }
        };
        mQueue.add(postRequest);
    }

    private void put(String api, final JSONObject input) {
        StringRequest putRequest = new StringRequest(Request.Method.PUT, Constants.HTTP_URL+api,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }

        };

        mQueue.add(putRequest);
    }

    private void delete(String api, final JSONObject input) {
        StringRequest dr = new StringRequest(Request.Method.DELETE, Constants.HTTP_URL+api,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }

        };
        mQueue.add(dr);
    }


}
