package com.mame.impression.server;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by kosukeEndo on 2016/01/02.
 */
public class ParameterUtil {
    public String getQuery(Map params) throws UnsupportedEncodingException
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
}
