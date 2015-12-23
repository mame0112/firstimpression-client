package com.mame.impression.server;

import android.util.Log;

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
import java.util.Iterator;
import java.util.Map;

//import org.apache.http.client.HttpClient;

//import org.apache.http.client.HttpClient;

/**
 * Created by kosukeEndo on 2015/12/15.
 */
public class WebApi {

    public void get(String api) throws IOException {
        LogUtil.d("WebApi", "connect");
        HttpURLConnection con = null;
        URL url = new URL(Constants.HTTP_URL + api);

        con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setInstanceFollowRedirects(false);
        con.setRequestProperty("Accept-Language", "jp");
        con.connect();

        Map headers = con.getHeaderFields();
        Iterator headerIt = headers.keySet().iterator();
        String header = null;
        while(headerIt.hasNext()){
            String headerKey = (String)headerIt.next();
            header += headerKey + "：" + headers.get(headerKey) + "\r\n";
        }

        LogUtil.d("WebApi", "header: " + header);

        InputStream in = con.getInputStream();
        byte bodyByte[] = new byte[1024];
        in.read(bodyByte);
        in.close();

        LogUtil.d("WebApi", "body: " + new String(bodyByte));

    }

    public void post(String api) throws IOException, JSONException {
        LogUtil.d("WebApi", "post");
        JSONObject requestJSON = new JSONObject();
        requestJSON.put("id", 1);
//        String requestJSON = "JSON文字列";

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(Constants.HTTP_URL + api).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(requestJSON.length());
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            Log.i("OSA030","doPost start.:" + conn.toString());

            conn.connect();

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(requestJSON.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
                StringBuffer responseJSON = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    responseJSON.append(inputLine);
                }
                Log.i("OSA030", "doPost success");
            }
        }catch(IOException e){
            Log.e("OSA030","error orz:" + e.getMessage(), e);
        }finally {
            if( conn != null ){
                conn.disconnect();
            }
        }

//        HttpURLConnection con = null;
//        URL url = new URL(Constants.HTTP_URL + api);
//
//        con = (HttpURLConnection)url.openConnection();
//        con.setRequestMethod("POST");
//        con.setDoOutput(true);
//        con.setInstanceFollowRedirects(false);
//        con.setRequestProperty("Accept-Language", "jp");
//        con.connect();
//
//        Map headers = con.getHeaderFields();
//        Iterator headerIt = headers.keySet().iterator();
//        String header = null;
//        while(headerIt.hasNext()){
//            String headerKey = (String)headerIt.next();
//            header += headerKey + "：" + headers.get(headerKey) + "\r\n";
//        }
//
//        LogUtil.d("WebApi", "header: " + header);
//
//        InputStream in = con.getInputStream();
//        byte bodyByte[] = new byte[1024];
//        in.read(bodyByte);
//        in.close();
//
//        LogUtil.d("WebApi", "body: " + new String(bodyByte));
    }

    public void put(String api) throws IOException {
        URL url = new URL(Constants.HTTP_URL + api);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write("Resource content");
        out.close();
        httpCon.getInputStream();
    }

    public void delete(String api) throws IOException {
        URL url = new URL(Constants.HTTP_URL + api);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded" );
        httpCon.setRequestMethod("DELETE");
        httpCon.connect();
    }
}
