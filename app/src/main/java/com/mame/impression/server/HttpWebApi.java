package com.mame.impression.server;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kosukeEndo on 2015/12/15.
 */
public class HttpWebApi implements WebApi {

    private static final String TAG = Constants.TAG + HttpWebApi.class.getSimpleName();

    protected RequestQueue mQueue;

    public HttpWebApi(Context context) {

        //ONly one mQueue should be created.
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(context);
        }
    }

    @Override
    public void get(final ResultListener listener, String api, final JSONObject input) {
        LogUtil.d(TAG, "get: " + input.toString());

        final JSONObject result;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, Constants.HTTP_URL + api, input,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        LogUtil.d(TAG, "result: " + response);
//                        return response;

                        //TODO
//                        listener.onCompleted(null);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
                    }
                }
        )
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "1");
                return params;
            }
        };

//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("id", "1");
//        map.put("param", "test");
//
//        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, Constants.HTTP_URL + api, map,
//            new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        LogUtil.d(TAG, "result: " + response);
//
//                        //Change JSONObject to ImpressionData
////                        listener.onCompleted();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
//                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
//                    }
//                }
//
//        ){
//
//                @Override
//                protected Map<String, String> getParams() {
//                    HashMap<String,String> map = new HashMap<String,String>();
//                    map.put("id", "1");
//                    map.put("param", "test");
//                    return map;
//                }
//
//        };

        mQueue.add(getRequest);

    };

    @Override
    public void post(final ResultListener listener, String api, final JSONObject input) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.HTTP_URL + api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(JsonConstants.PARAM, input.toString());

                return params;
            }
        };
        mQueue.add(postRequest);
    }

    @Override
    public void put(final ResultListener listener, String api, final JSONObject input) {
        StringRequest putRequest = new StringRequest(Request.Method.PUT, Constants.HTTP_URL + api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(JsonConstants.PARAM, input.toString());

                return params;
            }

        };

        mQueue.add(putRequest);
    }

    @Override
    public void delete(final ResultListener listener, String api, final JSONObject input) {
        StringRequest dr = new StringRequest(Request.Method.DELETE, Constants.HTTP_URL + api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        LogUtil.d(TAG, "result: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(JsonConstants.PARAM, input.toString());

                return params;
            }

        };
        mQueue.add(dr);
    }
}
