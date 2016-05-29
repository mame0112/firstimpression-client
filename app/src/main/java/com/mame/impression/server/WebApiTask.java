package com.mame.impression.server;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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

        private Accessor.AccessorListener mListener;

        private String mApi;

        private String mInput;

        public RestGet(Accessor.AccessorListener listener, String api, final String jsonInput){
            mListener = listener;
            mApi = api;
            mInput = jsonInput;
        }

        @Override
        public void run() {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(Constants.HTTP_API_URL + mApi+"?param=" + mInput);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                LogUtil.d(TAG, "string: " + total);

                if(mListener != null && total != null){
                    try {
                        mListener.onCompleted(new JSONObject(total.toString()));
                    } catch (JSONException e) {
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
        }
    }

    public static class RestPost implements Runnable {

        private Accessor.AccessorListener mListener;

        private String mApi;

        private String mInput;

        public RestPost(Accessor.AccessorListener listener, String api, final String input) {
            mListener = listener;
            mApi = api;
            mInput = input;
        }


        @Override
        public void run() {
            HttpURLConnection conn = null;
            URL url = null;
            try {

                url = new URL(Constants.HTTP_API_URL + mApi);
                conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream os = conn.getOutputStream();
                os.write(mInput.toString().getBytes("UTF-8"));
                os.close();

                String line;
                StringBuffer jsonString = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                LogUtil.d(TAG, "jsonString: " + jsonString);
                br.close();
                conn.disconnect();

                JSONObject resultJson = new JSONObject(jsonString.toString());
                mListener.onCompleted(resultJson);

            } catch (MalformedURLException e) {
                LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } catch (ProtocolException e) {
                LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } catch (IOException e) {
                LogUtil.d(TAG, "IOException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } finally {
                if(conn != null){
                    conn.disconnect();
                }
            }

        }
    }

    public static class RestPut implements Runnable {
        private Accessor.AccessorListener mListener;

        private String mApi;

        private String mInput;

        public RestPut(Accessor.AccessorListener listener, String api, final String input) {
            mListener = listener;
            mApi = api;
            mInput = input;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            URL url = null;
            try {

                url = new URL(Constants.HTTP_API_URL + mApi);
                conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setRequestMethod("PUT");

                OutputStream os = conn.getOutputStream();
                os.write(mInput.toString().getBytes("UTF-8"));
                os.close();

                String line;
                StringBuffer jsonString = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                LogUtil.d(TAG, "jsonString: " + jsonString);
                br.close();
                conn.disconnect();

                JSONObject resultJson = new JSONObject(jsonString.toString());
                mListener.onCompleted(resultJson);

            } catch (MalformedURLException e) {
                LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } catch (ProtocolException e) {
                LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } catch (IOException e) {
                LogUtil.d(TAG, "IOException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    public static class RestDelete implements Runnable {
        private Accessor.AccessorListener mListener;

        private String mApi;

        private String mInput;

        public RestDelete(Accessor.AccessorListener listener, String api, final String input) {
            mListener = listener;
            mApi = api;
            mInput = input;
        }

        @Override
        public void run() {
            LogUtil.d(TAG, "RestDelete run");
            HttpURLConnection conn = null;
            URL url = null;
            try {

                url = new URL(Constants.HTTP_API_URL + mApi+"?param=" + mInput.toString());
                conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty(
                        "Content-Type", "application/x-www-form-urlencoded" );

//                OutputStream os = conn.getOutputStream();
//                os.write(mInput.toString().getBytes("UTF-8"));
//                os.close();

                String line;
                StringBuffer jsonString = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                LogUtil.d(TAG, "jsonString: " + jsonString);
                br.close();
                conn.disconnect();

                JSONObject resultJson = new JSONObject(jsonString.toString());
                mListener.onCompleted(resultJson);

            } catch (MalformedURLException e) {
                LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } catch (ProtocolException e) {
                LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } catch (IOException e) {
                LogUtil.d(TAG, "IOException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                }
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
                if(mListener != null){
                    mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                }
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

}
