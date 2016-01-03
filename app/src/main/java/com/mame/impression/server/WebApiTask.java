package com.mame.impression.server;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kosukeEndo on 2016/01/02.
 */
public class WebApiTask {

    private final static String TAG = Constants.TAG + WebApiTask.class.getSimpleName();

    public static class RestGet implements Runnable{

        private ResultListener mListener;

        private String mApi;

        private JSONObject mInput;

        public RestGet(ResultListener listener, String api, final JSONObject input){
            mListener = listener;
            mApi = api;
            mInput = input;
        }

        @Override
        public void run() {

        }



//        @Override
//        public void run() {
//            LogUtil.d(TAG, "Get run");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    URL url = null;
//                    HttpURLConnection urlConnection = null;
//                    try {
//                        url = new URL(Constants.API_URL + mApi+"?id=2");
//                        urlConnection = (HttpURLConnection) url.openConnection();
//                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                        BufferedReader r = new BufferedReader(new InputStreamReader(in));
//                        StringBuilder total = new StringBuilder();
//                        String line;
//                        while ((line = r.readLine()) != null) {
//                            total.append(line);
//                        }
//                        LogUtil.d(TAG, "string: " + line);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if(urlConnection != null){
//                            urlConnection.disconnect();
//                        }
//                    }
//
//                }
//            }).start();
//        }
    }

    public static class RestPost implements Runnable {

        private ResultListener mListener;

        private String mApi;

        private JSONObject mInput;

        public RestPost(ResultListener listener, String api, final JSONObject input) {
            mListener = listener;
            mApi = api;
            mInput = input;
        }

        @Override
        public void run() {
            LogUtil.d(TAG, "RestPost run");
            HttpURLConnection connection = null;
            try {

                JSONObject body = new JSONObject();
                body.put("id", "1");
                body.put("param", "body");
//                URL url = new URL("http://first-impression-backend.appspot.com/jsonrpc");
                URL url = new URL(Constants.API_URL + "/user");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Authorization", "Basic" + encode);
//                connection.setRequestProperty("Content-Type", contentType);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Length",
                        "" + Integer.toString(body.toString().getBytes().length));

                connection.setUseCaches(false);
                connection.setDoInput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                writer.write(body.toString());
                writer.close();
                String line;
                StringBuffer jsonString = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                LogUtil.d(TAG, "jsonString: " + jsonString);
                br.close();
                connection.disconnect();
            } catch (IOException ioE) {
                LogUtil.d(TAG, "IOException: " + ioE.getMessage());
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
        }
    }

    public static class RestPut implements Runnable {
        private ResultListener mListener;

        private String mApi;

        private JSONObject mInput;

        public RestPut(ResultListener listener, String api, final JSONObject input) {
            mListener = listener;
            mApi = api;
            mInput = input;
        }

        @Override
        public void run() {
            LogUtil.d(TAG, "put");
            URL url = null;
            HttpURLConnection conn = null;
            try {

                JSONObject body = new JSONObject();
                body.put("id", "1");
                body.put("param", "body");

                url = new URL(Constants.API_URL + mApi);
                conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
//                conn.setUseCaches(false);
//                conn.setDoInput(true);
                conn.setRequestMethod("PUT");
//                conn.setRequestProperty("Accept", "application/json");
//                conn.setRequestProperty("Content-Length", Integer.toString(body.toString().getBytes().length));


//                        OutputStream os = conn.getOutputStream();
//                        BufferedWriter writer = new BufferedWriter(
//                                new OutputStreamWriter(os, "UTF-8"));
//                        Map<String, Object> values = new HashMap<String, Object>();
//                        values.put("username", "test name");
//                        values.put("password", "test password");
//                        writer.write(new ParameterUtil().getQuery(values));
//
//                        writer.flush();
//                        writer.close();
//                        os.close();

//                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(body.toString());
                writer.close();
                String line;
                StringBuffer jsonString = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                LogUtil.d(TAG, "jsonString: " + jsonString);
                br.close();
                conn.disconnect();

                } catch (MalformedURLException e) {
                    LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    LogUtil.d(TAG, "IOException: " + e.getMessage());
                } catch (JSONException e) {
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                } finally {
                    if(conn != null){
                        conn.disconnect();
                    }
                }

            //            OutputStreamWriter out = new OutputStreamWriter(
//                    conn.getOutputStream());
//            out.write("Resource content");
//            out.close();
//                        int responseCode = conn.getResponseCode();
//                        LogUtil.d(TAG, "responseCode: " + responseCode);

//                        String response = null;
//
//                        if (responseCode == HttpsURLConnection.HTTP_OK) {
//                            String line;
//                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                            while ((line = br.readLine()) != null) {
//                                response += line;
//                            }
//                        } else {
//                            response = "";
//
//                        }

        }

        }


}
