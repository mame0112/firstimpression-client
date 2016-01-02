package com.mame.impression.server;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.mame.impression.constant.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kosukeEndo on 2016/01/01.
 */
public class ListRequest extends Request<String> {

    private final static String TAG = Constants.TAG + ListRequest.class.getSimpleName();

    private Response.ErrorListener mListener;

    private Map<String, String> mParams;

    public ListRequest(String param1, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, Constants.HTTP_URL + "/list", errorListener);
        mParams = new HashMap<String, String>();
        mParams.put("id", "1");

    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(String response) {
//        mListener.
    }
}
