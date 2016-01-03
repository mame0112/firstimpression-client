package com.mame.impression.server;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kosukeEndo on 2016/01/01.
 */
public class ListRequest extends Request<String> {

    private final static String TAG = Constants.TAG + ListRequest.class.getSimpleName();

    private Response.ErrorListener mListener;

    private Map<String, String> mParams;

    public ListRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, Constants.API_URL + "/user", errorListener);
//        mParams = new HashMap<String, String>();
//        mParams.put("id", "1");

    }

    public void setParams(Map<String, String> map) {
        mParams = map;
    }

    @Override
    public Map<String, String> getParams() {
        LogUtil.d(TAG, "getParams");
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        LogUtil.d(TAG, "getHeaders");
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("User-agent", System.getProperty("http.agent"));
        return headers;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String resp = new String(response.data);
        JSONObject resultJson;
        try {
            resultJson = new JSONObject(resp);
        } catch (Exception e) {
            return null;
        }
        return Response.success(resultJson, getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        LogUtil.d(TAG, "response: " + response);
//        mListener.
    }
}
