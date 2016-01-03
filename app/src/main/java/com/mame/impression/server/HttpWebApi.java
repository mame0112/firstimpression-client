package com.mame.impression.server;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kosukeEndo on 2015/12/15.
 */
public class HttpWebApi implements WebApi {

    private static final String TAG = Constants.TAG + HttpWebApi.class.getSimpleName();

    public HttpWebApi(Context context) {
    }

    @Override
    public void get(ResultListener listener, final String api, JSONObject input) {
        LogUtil.d(TAG, "get");

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(Constants.API_URL + api+"?id=2");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                    LogUtil.d(TAG, "string: " + line);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }

            }
        }).start();
     }

    @Override
    public void post(ResultListener listener, final String api, JSONObject input) {
        LogUtil.d(TAG, "post: " + Constants.HTTP_URL + api);

        new Thread(new Runnable() {
            @Override
            public void run() {
            URL url = null;
            HttpURLConnection conn = null;
            try {
                url = new URL(Constants.API_URL + api);
                conn = (HttpURLConnection) url.openConnection();
    //            urlConnection.setChunkedStreamingMode(0);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");

    //            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
    //            writeStream(out);
    //
    //            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    //            readStream(in);
    //            List<NameValuePair> params = new ArrayList<NameValuePair>();
    //            params.add(new BasicNameValuePair("firstParam", paramValue1));
    //            params.add(new BasicNameValuePair("secondParam", paramValue2));
    //            params.add(new BasicNameValuePair("thirdParam", paramValue3));
    //            ContentValues values=new ContentValues();
    //            values.put("username","test name");
    //            values.put("password","test password");

//                Map<String, Object> values = new HashMap<String, Object>();
//                values.put("username","test name");
//                values.put("password","test password");

                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("id", 1);
                    jsonParam.put("param", new JSONObject());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DataOutputStream printout = new DataOutputStream(conn.getOutputStream ());
                printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                printout.flush ();
                printout.close ();

//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(values));
//                writer.flush();
//                writer.close();
//                os.close();
                int responseCode=conn.getResponseCode();
                LogUtil.d(TAG, "responseCode: " + responseCode);

                String response = null;

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";

                }

                LogUtil.d(TAG, "response: " + response);

//                conn.connect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                LogUtil.d(TAG, "IOException: " + e.getMessage());                }

            }
        }).start();

    }

    private String buildPostParameters(Object content) {
        String output = null;
        if ((content instanceof String) ||
                (content instanceof JSONObject) ||
                (content instanceof JSONArray)) {
            output = content.toString();
        } else if (content instanceof Map) {
            Uri.Builder builder = new Uri.Builder();
            HashMap hashMap = (HashMap) content;
            if (hashMap != null) {
                Iterator entries = hashMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    entries.remove(); // avoids a ConcurrentModificationException
                }
                output = builder.build().getEncodedQuery();
            }
        }

        return output;
    }

    private String getQuery(Map params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String, Object> e : ((Map<String, Object>)params).entrySet()) {
            if (first){
                first = false;
            }else {
                result.append("&");
            }

            result.append(URLEncoder.encode(e.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(e.getValue()), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    public void put(ResultListener listener, final String api, JSONObject input) {
        LogUtil.d(TAG, "put");
        URL url = null;
        try {
            url = new URL(Constants.API_URL + api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            Map<String, Object> values = new HashMap<String, Object>();
            values.put("username","test name");
            values.put("password","test password");
            writer.write(getQuery(values));

            writer.flush();
            writer.close();
            os.close();


//            OutputStreamWriter out = new OutputStreamWriter(
//                    conn.getOutputStream());
//            out.write("Resource content");
//            out.close();
            int responseCode=conn.getResponseCode();
            LogUtil.d(TAG, "responseCode: " + responseCode);

            String response = null;

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

            LogUtil.d(TAG, "response: " + response);
            LogUtil.d(TAG, "response: " + response);
        } catch (MalformedURLException e) {
            LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
        } catch (IOException e) {
            LogUtil.d(TAG, "IOException: " + e.getMessage());
        }

    }

    @Override
    public void delete(ResultListener listener, String api, JSONObject input) {
        URL url = null;
        try {
            url = new URL(Constants.API_URL + api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("DELETE");
            conn.connect();
        } catch (MalformedURLException e) {
            LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
        } catch (IOException e) {
            LogUtil.d(TAG, "IOException: " + e.getMessage());
        }

    }

//    protected RequestQueue mQueue;
//
//    public HttpWebApi(Context context) {
//
//        //ONly one mQueue should be created.
//        if(mQueue == null){
//            mQueue = Volley.newRequestQueue(context);
//        }
//    }
//
//    @Override
//    public void get(final ResultListener listener, String api, final JSONObject input) {
//        LogUtil.d(TAG, "get: " + input.toString());
//
////        HashMap<String, String> params = new HashMap<String, String>();
////        params.put("id", "1");
////        JSONObject obj = new JSONObject(params);
//
////        LogUtil.d(TAG, "obj: " + obj.toString());
//
////        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, Constants.HTTP_URL + api, null,
////                new Response.Listener<JSONObject>() {
////                    @Override
////                    public void onResponse(JSONObject response) {
////                        // display response
////                        LogUtil.d(TAG, "result: " + response);
//////                        return response;
////
////                        //TODO
//////                        listener.onCompleted(null);
////
////
////
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
////                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
////                    }
////                }
////        )
//// {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("id", "1");
////                params.put("param", "aa");
////                return params;
////            }
////        };
//
//
//        StringRequest request = new StringRequest(
//                //送信方法 GET/POST
//                Request.Method.GET,
//                //送信先
//                Constants.HTTP_URL + api,
//                //レスポンス成功時のリスナー
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //レスポンス受け取り時の処理
//                        if (response == null){
//                            //空の場合
//                            return;
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", "1");
//                params.put("param", "aa");
//                return params;
//            }
//        };
//
//
//        mQueue.add(request);
//
//    };
//
//    @Override
//    public void post(final ResultListener listener, String api, final JSONObject input) {
//        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.HTTP_URL + api,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        LogUtil.d(TAG, "result: " + response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
//                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(JsonConstants.PARAM, input.toString());
//
//                return params;
//            }
//        };
//        mQueue.add(postRequest);
//    }
//
//    @Override
//    public void put(final ResultListener listener, String api, final JSONObject input) {
//        StringRequest putRequest = new StringRequest(Request.Method.PUT, Constants.HTTP_URL + api,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        LogUtil.d(TAG, "result: " + response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
//                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
//                    }
//                }
//        ) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(JsonConstants.PARAM, input.toString());
//
//                return params;
//            }
//
//        };
//
//        mQueue.add(putRequest);
//    }
//
//    @Override
//    public void delete(final ResultListener listener, String api, final JSONObject input) {
//        StringRequest dr = new StringRequest(Request.Method.DELETE, Constants.HTTP_URL + api,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        LogUtil.d(TAG, "result: " + response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtil.d(TAG, "onErrorResponse: " + error.getMessage());
//                        listener.onFailed(ImpressionError.GENERAL_ERROR, error.getMessage());
//                    }
//                }
//        ) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(JsonConstants.PARAM, input.toString());
//
//                return params;
//            }
//
//        };
//        mQueue.add(dr);
//    }
}
