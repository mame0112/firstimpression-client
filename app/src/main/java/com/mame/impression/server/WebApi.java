package com.mame.impression.server;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.apache.http.client.HttpClient;

//import org.apache.http.client.HttpClient;

/**
 * Created by kosukeEndo on 2015/12/15.
 */
public class WebApi extends AbstractWebApi{

    private static final String TAG = Constants.TAG + WebApi.class.getSimpleName();

    @Override
    public void get(String api, final JSONObject input) {
        LogUtil.d(TAG, "get");

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

    @Override
    public void post(String api, final JSONObject input) {
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

    @Override
    public void put(String api, final JSONObject input) {
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

    @Override
    public void delete(String api, final JSONObject input) {
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
