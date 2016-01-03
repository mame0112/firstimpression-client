package com.mame.impression.server;

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
            LogUtil.d(TAG, "Get run");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    HttpURLConnection urlConnection = null;
                    try {
                        url = new URL(Constants.API_URL + mApi+"?id=2");
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
    }

    public static class RestPost implements Runnable{

        private ResultListener mListener;

        private String mApi;

        private JSONObject mInput;

        public RestPost(ResultListener listener, String api, final JSONObject input){
            mListener = listener;
            mApi = api;
            mInput = input;
        }

        @Override
        public void run() {
            LogUtil.d(TAG, "Post run..");

            URL url = null;
            HttpURLConnection conn = null;
            try {

                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("id", 1);
                    jsonParam.put("param", new JSONObject());
                } catch (JSONException e) {
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                }

                url = new URL(Constants.API_URL + mApi);
                conn = (HttpURLConnection) url.openConnection();
                //            urlConnection.setChunkedStreamingMode(0);
//                        conn.setReadTimeout(10000);
//                        conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setFixedLengthStreamingMode(jsonParam.toString().getBytes().length);
//                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                        conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                conn.setUseCaches(false);
//                conn.connect();

                LogUtil.d(TAG, conn.toString());

                conn.connect();

                LogUtil.d(TAG, "jsonParam.toString(): " + jsonParam.toString());

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

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
            } catch (MalformedURLException e) {
                e.printStackTrace();
                LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.d(TAG, "IOException: " + e.getMessage());
            } finally {

            }



//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//
//                    URL url = null;
//                    HttpURLConnection conn = null;
//                    try {
//                        url = new URL(Constants.HTTP_URL + mApi);
//                        conn = (HttpURLConnection) url.openConnection();
//                        //            urlConnection.setChunkedStreamingMode(0);
////                        conn.setReadTimeout(10000);
////                        conn.setConnectTimeout(15000);
//                        conn.setRequestMethod("POST");
//                        conn.setRequestProperty("Content-Type", "application/json");
////                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////                        conn.setRequestProperty("Accept", "application/json");
//                        conn.setDoInput(true);
//                        conn.setDoOutput(true);
//                        conn.setUseCaches(false);
//                        conn.connect();;
////                        conn.connect();
//
//                        //            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//                        //            writeStream(out);
//                        //
//                        //            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                        //            readStream(in);
//                        //            List<NameValuePair> params = new ArrayList<NameValuePair>();
//                        //            params.add(new BasicNameValuePair("firstParam", paramValue1));
//                        //            params.add(new BasicNameValuePair("secondParam", paramValue2));
//                        //            params.add(new BasicNameValuePair("thirdParam", paramValue3));
//                        //            ContentValues values=new ContentValues();
//                        //            values.put("username","test name");
//                        //            values.put("password","test password");
//
////                        Map<String, Object> values = new HashMap<String, Object>();
////                        values.put("username","test name");
////                        values.put("password","test password");
//
//                        JSONObject jsonParam = new JSONObject();
//                        try {
//                            jsonParam.put("id", 1);
//                            jsonParam.put("param", new JSONObject());
//                        } catch (JSONException e) {
//                            LogUtil.d(TAG, "JSONException: " + e.getMessage());
//                        }
//
//                        OutputStream os = conn.getOutputStream();
//                        os.write(jsonParam.toString().getBytes());
//                        os.flush();
//
////                        conn.setRequestProperty( "Content-Length", jsonParam.toString());
//
////                        DataOutputStream printout = new DataOutputStream(conn.getOutputStream());
////                        byte[] data=jsonParam.toString().getBytes("UTF-8");
////                        LogUtil.d(TAG, "byte length: " + data.length);
////                        printout.write(data);
////                        printout.writeBytes(jsonParam.toString());
////                        LogUtil.d(TAG, "jsonstring: " + jsonParam.toString());
////                        printout.writeUTF(URLEncoder.encode(jsonParam.toString(),"UTF-8"));
////                        printout.flush ();
////                        printout.close ();
//
//
////                        OutputStream os = conn.getOutputStream();
////                        BufferedWriter writer = new BufferedWriter(
////                                new OutputStreamWriter(os, "UTF-8"));
////                        writer.write(new ParameterUtil().getQuery(values));
////                        writer.flush();
////                        writer.close();
////                        os.close();
//                        int responseCode=conn.getResponseCode();
//                        LogUtil.d(TAG, "responseCode: " + responseCode);
//
//                        String response = null;
//
//                        if (responseCode == HttpsURLConnection.HTTP_OK) {
//                            String line;
//                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                            while ((line=br.readLine()) != null) {
//                                response+=line;
//                            }
//                        }
//                        else {
//                            response="";
//
//                        }
//
//                        LogUtil.d(TAG, "response: " + response);
//
////                conn.connect();
//
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                        LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        LogUtil.d(TAG, "IOException: " + e.getMessage());                }
//
//                }
//            }).start();
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
            LogUtil.d(TAG, "Put run");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d(TAG, "put");
                    URL url = null;
                    try {
                        url = new URL(Constants.API_URL + mApi);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("PUT");

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));

                        Map<String, Object> values = new HashMap<String, Object>();
                        values.put("username", "test name");
                        values.put("password", "test password");
                        writer.write(new ParameterUtil().getQuery(values));

                        writer.flush();
                        writer.close();
                        os.close();


//            OutputStreamWriter out = new OutputStreamWriter(
//                    conn.getOutputStream());
//            out.write("Resource content");
//            out.close();
                        int responseCode = conn.getResponseCode();
                        LogUtil.d(TAG, "responseCode: " + responseCode);

                        String response = null;

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line = br.readLine()) != null) {
                                response += line;
                            }
                        } else {
                            response = "";

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
            }).start();
        }
    }

}
