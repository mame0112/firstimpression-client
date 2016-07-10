package com.mame.impression.server;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.Accessor;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public class HttpsWebApi extends WebApiBase implements WebApi {

    private static final String TAG = Constants.TAG + HttpsWebApi.class.getSimpleName();

    public HttpsWebApi(WebApiListener listener) {
        super(listener);
    }

    @Override
    public Runnable get(final WebApiBase.WebApiListener listener, final String api, final String input) {
        LogUtil.d(TAG, "HTTPS get");

        return new Runnable() {
            @Override
            public void run() {

                URL url = null;
                HttpsURLConnection urlConnection = null;
                try {
                    url = new URL(Constants.HTTPS_API_URL + api+"?param=" + input);
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                    LogUtil.d(TAG, "string: " + total);

                    if(listener != null && total != null){
                        try {
                            listener.onCompleted(new JSONObject(total.toString()));
                        } catch (JSONException e) {
                            LogUtil.d(TAG, "JSONException: " + e.getMessage());
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    if(listener != null){
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(listener != null){
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            }
        };

    }

    @Override
    public Runnable post(final WebApiBase.WebApiListener listener, final String api, final String input) {
        LogUtil.d(TAG, "post: " + Constants.HTTP_URL + api);

        return new Runnable() {

            @Override
            public void run() {
                HttpsURLConnection conn = null;
                URL url = null;
                try {

                    url = new URL(Constants.HTTPS_API_URL + api);
                    conn = (HttpsURLConnection) url.openConnection();

                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");

                    OutputStream os = conn.getOutputStream();
                    os.write(input.toString().getBytes("UTF-8"));
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
                    listener.onCompleted(resultJson);

                } catch (MalformedURLException e) {
                    LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                    if(listener != null){
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } catch (ProtocolException e) {
                    LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                    if(listener != null){
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } catch (IOException e) {
                    LogUtil.d(TAG, "IOException: " + e.getMessage());
                    if(listener != null){
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } catch (JSONException e) {
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    if(listener != null){
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } finally {
                    if(conn != null){
                        conn.disconnect();
                    }
                }

            }
        };

    }

    @Override
    public Runnable put(final WebApiBase.WebApiListener listener, final String api, final String input) {
        LogUtil.d(TAG, "put");

        return new Runnable() {

            @Override
            public void run() {
                HttpsURLConnection conn = null;
                URL url = null;
                try {

                    url = new URL(Constants.HTTPS_API_URL + api);
                    conn = (HttpsURLConnection) url.openConnection();

                    conn.setDoOutput(true);
                    conn.setRequestMethod("PUT");

                    OutputStream os = conn.getOutputStream();
                    os.write(input.toString().getBytes("UTF-8"));
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
                    listener.onCompleted(resultJson);

                } catch (MalformedURLException e) {
                    LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } catch (ProtocolException e) {
                    LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } catch (IOException e) {
                    LogUtil.d(TAG, "IOException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } catch (JSONException e) {
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }

        };

    }

    @Override
    public Runnable delete(final WebApiBase.WebApiListener listener, final String api, final String input) {

        return new Runnable() {

            @Override
            public void run() {
                LogUtil.d(TAG, "RestDelete run");
                HttpsURLConnection conn = null;
                URL url = null;
                try {

                    url = new URL(Constants.HTTPS_API_URL + api + "?param=" + input.toString());
                    conn = (HttpsURLConnection) url.openConnection();

                    conn.setDoOutput(true);
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty(
                            "Content-Type", "application/x-www-form-urlencoded");

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
                    listener.onCompleted(resultJson);

                } catch (MalformedURLException e) {
                    LogUtil.d(TAG, "MalformedURLException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } catch (ProtocolException e) {
                    LogUtil.d(TAG, "MProtocolException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } catch (IOException e) {
                    LogUtil.d(TAG, "IOException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.GENERAL_ERROR, e.getMessage());
                    }
                } catch (JSONException e) {
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    if (listener != null) {
                        listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        };
    }
}
